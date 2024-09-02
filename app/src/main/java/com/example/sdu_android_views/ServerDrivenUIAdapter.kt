package com.example.sdu_android_views

/**
Created by Masum Mehedi on 9/1/2024.
masumehedissl@gmail.com
 **/

import android.animation.ValueAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.databinding.ItemMultiColorSelectBinding
import com.example.sdu_android_views.databinding.ItemMultiSelectBinding
import com.example.sdu_android_views.databinding.ItemMultiSelectTagsBinding
import com.example.sdu_android_views.databinding.ItemPriceRangeBinding
import com.example.sdu_android_views.databinding.ItemSingleColorSelectBinding
import com.example.sdu_android_views.databinding.ItemSingleSelectBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider
import com.google.gson.Gson


class ServerDrivenUIAdapter : ListAdapter<UIElement, RecyclerView.ViewHolder>(UIDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UIElement.MultiSelect -> 0
            is UIElement.PriceRange -> 1
            is UIElement.MultiColorSelect -> 2
            is UIElement.SingleSelect -> 3
            is UIElement.SingleColorSelect -> 4
            is UIElement.MultiSelectTags -> 5
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> MultiSelectViewHolder(
                ItemMultiSelectBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            1 -> RangeViewHolder(
                ItemPriceRangeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            2 -> MultiColorSelectViewHolder(
                ItemMultiColorSelectBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            3 -> SingleSelectViewHolder(
                ItemSingleSelectBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            4 -> SingleColorSelectViewHolder(
                ItemSingleColorSelectBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            5 -> MultiSelectTagsViewHolder(
                ItemMultiSelectTagsBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val uiElement = getItem(position)) {
            is UIElement.MultiSelect -> (holder as MultiSelectViewHolder).bind(uiElement)
            is UIElement.PriceRange -> (holder as RangeViewHolder).bind(uiElement)
            is UIElement.MultiColorSelect -> (holder as MultiColorSelectViewHolder).bind(uiElement)
            is UIElement.SingleSelect -> (holder as SingleSelectViewHolder).bind(uiElement)
            is UIElement.SingleColorSelect -> (holder as SingleColorSelectViewHolder).bind(uiElement)
            is UIElement.MultiSelectTags -> (holder as MultiSelectTagsViewHolder).bind(uiElement)
        }
    }

    class MultiSelectViewHolder(private val binding: ItemMultiSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UIElement.MultiSelect) {

            binding.titleTextView.text = item.title
            binding.optionsLayout.removeAllViews()
            item.options?.let { options ->
                for (i in 0 until options.length()) {
                    val option = options.getJSONObject(i)
                    val checkBox = CheckBox(itemView.context).apply {
                        text = option.getString("title")
                        id = option.getInt("id")
                    }
                    binding.optionsLayout.addView(checkBox)
                }
            }

            binding.titleTextView.setOnClickListener {
                binding.optionsLayout.toggleExpandCollapse()
            }


        }
    }

    class RangeViewHolder(private val binding: ItemPriceRangeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIElement.PriceRange) {
            binding.apply {
                titleTextView.text = item.title
                //  val rangeSlider = RangeSlider(binding.root.context)
                val rangeSeekBarLayout = binding.rangeSeekBarLayout
                val minPrice = item.rangeData.getDouble("min_price").toFloat()
                val maxPrice = item.rangeData.getDouble("max_price").toFloat()
                // Create the RangeSlider programmatically
                val rangeSlider = RangeSlider(binding.root.context).apply {
                    valueFrom = minPrice
                    valueTo = maxPrice
                    setValues(20f, 80f)
                    thumbRadius = 20
                    thumbHeight = 40
                    thumbStrokeColor = getColorStateList(binding.root.context, R.color.black)
                    thumbStrokeWidth = 1.0f
                    trackHeight = 10
                    stepSize=1f
                    isTickVisible=false



                    // Customize the thumb and track color
                    trackActiveTintList =
                        getColorStateList(binding.root.context, R.color.purple_500)
                    trackInactiveTintList = getColorStateList(binding.root.context, R.color.gray)
                    thumbTintList = getColorStateList(binding.root.context, R.color.purple_500)
                }

                // Add a listener to get the range slider changes
                rangeSlider.addOnChangeListener { slider, _, _ ->
                    // Handle slider value changes
                    val values = slider.values
                    println("Start: ${values[0]}, End: ${values[1]}")
                    setMin(values[0].toInt())
                    setMax(values[1].toInt())
                }

                // Add the RangeSlider to the layout
                rangeSeekBarLayout.addView(rangeSlider)
                titleTextView.setOnClickListener {
                    rangeSeekBarLayout.toggleExpandCollapse()
                }


            }
        }

        private fun setMin(min: Int) {
            binding.edtMin.setText(min.toString())
        }

        private fun setMax(max: Int) {
            binding.edtMax.setText(max.toString())
        }

    }


    class MultiColorSelectViewHolder(private val binding: ItemMultiColorSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var recyclerView: RecyclerView
        private lateinit var colorAdapter: ColorMultiSelectionAdapter
        private val colors: MutableList<ColorItem> = mutableListOf()

        fun bind(item: UIElement.MultiColorSelect) {
            colorAdapter = ColorMultiSelectionAdapter()
            recyclerView = binding.recyclerView
            var isRecyclerViewVisible = true
            binding.apply {
                titleTextView.text = item.title
                titleTextView.setOnClickListener {
                    if (isRecyclerViewVisible) {
                        recyclerView.visibility = View.GONE
                    } else {
                        recyclerView.visibility = View.VISIBLE
                    }
                    isRecyclerViewVisible = !isRecyclerViewVisible
                }
            }



            recyclerView.layoutManager = LinearLayoutManager(binding.root.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL

            }
            recyclerView.adapter = colorAdapter
            item.options?.let { options ->
                for (i in 0 until options.length()) {
                    val colorJson = options.getJSONObject(i).toString()
                    val gson = Gson()
                    val colorItem: ColorItem = gson.fromJson(colorJson, ColorItem::class.java)

                    colors.add(colorItem)
                }


                colorAdapter.submitList(colors)
            }


            colorAdapter.submitList(colors)



            colorAdapter.setOnColorSelectedListener {

            }
        }
    }

    class SingleSelectViewHolder(private val binding: ItemSingleSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UIElement.SingleSelect) {
            binding.apply {
                titleTextView.text = item.title

                titleTextView.setOnClickListener {
                    radioGroup.toggleExpandCollapse()
                }


                radioGroup.removeAllViews()
                item.options?.let { options ->
                    for (i in 0 until options.length()) {
                        val option = options.getJSONObject(i)
                        val radioButton = RadioButton(itemView.context).apply {
                            text = option.getString("title")
                            id = option.getInt("id")
                        }
                        radioGroup.addView(radioButton)
                    }
                }
            }
        }
    }

    class SingleColorSelectViewHolder(val binding: ItemSingleColorSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var recyclerView: RecyclerView
        private lateinit var colorAdapter: ColorSelectionAdapter
        private val colors: MutableList<ColorItem> = mutableListOf()
        fun bind(item: UIElement.SingleColorSelect) {
            colorAdapter = ColorSelectionAdapter()
            recyclerView = binding.recyclerView
            binding.titleTextView.text = item.title

            // Initialize RecyclerView layout manager and adapter
            recyclerView.layoutManager = LinearLayoutManager(binding.root.context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            recyclerView.adapter = colorAdapter

            // Populate RecyclerView with data
            val colors = mutableListOf<ColorItem>()
            item.options?.let { options ->
                for (i in 0 until options.length()) {
                    val colorJson = options.getJSONObject(i).toString()
                    val colorItem: ColorItem = Gson().fromJson(colorJson, ColorItem::class.java)
                    colors.add(colorItem)
                }
                colorAdapter.submitList(colors)
            }

            var isRecyclerViewVisible = true
            binding.apply {
                titleTextView.text = item.title
                titleTextView.setOnClickListener {
                    if (isRecyclerViewVisible) {
                        recyclerView.visibility = View.GONE
                    } else {
                        recyclerView.visibility = View.VISIBLE
                    }
                    isRecyclerViewVisible = !isRecyclerViewVisible
                }
            }


        }


    }

    class MultiSelectTagsViewHolder(binding: ItemMultiSelectTagsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val titleTextView: TextView = binding.titleTextView
        private val chipGroup: ChipGroup = binding.chipGroup

        fun bind(item: UIElement.MultiSelectTags) {
            titleTextView.text = item.title
            chipGroup.removeAllViews()
            item.options?.let { options ->
                for (i in 0 until options.length()) {
                    val option = options.getJSONObject(i)
                    val chip = Chip(itemView.context).apply {
                        text = option.getString("title")
                        id = option.getInt("id")
                        isCheckable = true
                    }
                    chipGroup.addView(chip)

                }
            }
            titleTextView.setOnClickListener {
                chipGroup.toggleExpandCollapse()
            }

        }
    }

    class UIDiffCallback : DiffUtil.ItemCallback<UIElement>() {
        override fun areItemsTheSame(oldItem: UIElement, newItem: UIElement): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UIElement, newItem: UIElement): Boolean {
            return oldItem == newItem
        }
    }


}

