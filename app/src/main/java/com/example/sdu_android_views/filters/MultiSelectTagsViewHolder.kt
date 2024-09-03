package com.example.sdu_android_views.filters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.UIElement
import com.example.sdu_android_views.databinding.ItemMultiSelectTagsBinding
import com.example.sdu_android_views.toggleExpandCollapse
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
Created by Masum Mehedi on 9/3/2024.
masumehedissl@gmail.com
 **/

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