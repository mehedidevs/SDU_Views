package com.example.sdu_android_views.filters

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.ColorItem
import com.example.sdu_android_views.ColorMultiSelectionAdapter
import com.example.sdu_android_views.UIElement
import com.example.sdu_android_views.databinding.ItemMultiColorSelectBinding
import com.google.gson.Gson

/**
Created by Masum Mehedi on 9/3/2024.
masumehedissl@gmail.com
 **/
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