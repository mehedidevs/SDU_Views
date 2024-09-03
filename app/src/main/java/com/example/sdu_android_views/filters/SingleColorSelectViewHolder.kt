package com.example.sdu_android_views.filters

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.ColorItem
import com.example.sdu_android_views.ColorSelectionAdapter
import com.example.sdu_android_views.UIElement
import com.example.sdu_android_views.databinding.ItemSingleColorSelectBinding
import com.google.gson.Gson

/**
Created by Masum Mehedi on 9/3/2024.
masumehedissl@gmail.com
 **/
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