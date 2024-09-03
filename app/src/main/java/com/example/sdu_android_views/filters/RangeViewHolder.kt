package com.example.sdu_android_views.filters

import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.R
import com.example.sdu_android_views.UIElement
import com.example.sdu_android_views.databinding.ItemPriceRangeBinding
import com.example.sdu_android_views.toggleExpandCollapse
import com.google.android.material.slider.RangeSlider

/**
Created by Masum Mehedi on 9/3/2024.
masumehedissl@gmail.com
 **/
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