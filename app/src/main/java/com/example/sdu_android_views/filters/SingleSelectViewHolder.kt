package com.example.sdu_android_views.filters

import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.UIElement
import com.example.sdu_android_views.databinding.ItemSingleSelectBinding
import com.example.sdu_android_views.toggleExpandCollapse

/**
Created by Masum Mehedi on 9/3/2024.
masumehedissl@gmail.com
 **/
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