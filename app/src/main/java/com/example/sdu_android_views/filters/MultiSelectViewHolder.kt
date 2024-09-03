package com.example.sdu_android_views.filters

import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.UIElement
import com.example.sdu_android_views.databinding.ItemMultiSelectBinding
import com.example.sdu_android_views.toggleExpandCollapse

/**
Created by Masum Mehedi on 9/3/2024.
masumehedissl@gmail.com
 **/

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