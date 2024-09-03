package com.example.sdu_android_views

/**
Created by Masum Mehedi on 9/1/2024.
masumehedissl@gmail.com
 **/
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.databinding.ItemMultiColorSelectBinding
import com.example.sdu_android_views.databinding.ItemMultiSelectBinding
import com.example.sdu_android_views.databinding.ItemMultiSelectTagsBinding
import com.example.sdu_android_views.databinding.ItemPriceRangeBinding
import com.example.sdu_android_views.databinding.ItemSingleColorSelectBinding
import com.example.sdu_android_views.databinding.ItemSingleSelectBinding
import com.example.sdu_android_views.filters.MultiColorSelectViewHolder
import com.example.sdu_android_views.filters.MultiSelectTagsViewHolder
import com.example.sdu_android_views.filters.MultiSelectViewHolder
import com.example.sdu_android_views.filters.RangeViewHolder
import com.example.sdu_android_views.filters.SingleColorSelectViewHolder
import com.example.sdu_android_views.filters.SingleSelectViewHolder


class ServerDrivenUIAdapter : ListAdapter<UIElement, RecyclerView.ViewHolder>(UIDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UIElement.MultiSelect -> MultiSelect
            is UIElement.PriceRange -> PriceRange
            is UIElement.MultiColorSelect -> MultiColorSelect
            is UIElement.SingleSelect -> SingleSelect
            is UIElement.SingleColorSelect -> SingleColorSelect
            is UIElement.MultiSelectTags -> MultiSelectTags
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MultiSelect -> MultiSelectViewHolder(
                ItemMultiSelectBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            PriceRange -> RangeViewHolder(
                ItemPriceRangeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            MultiColorSelect -> MultiColorSelectViewHolder(
                ItemMultiColorSelectBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            SingleSelect -> SingleSelectViewHolder(
                ItemSingleSelectBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            SingleColorSelect -> SingleColorSelectViewHolder(
                ItemSingleColorSelectBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            MultiSelectTags -> MultiSelectTagsViewHolder(
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


    class UIDiffCallback : DiffUtil.ItemCallback<UIElement>() {
        override fun areItemsTheSame(oldItem: UIElement, newItem: UIElement): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UIElement, newItem: UIElement): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val MultiSelect = 0
        const val PriceRange = 1
        const val MultiColorSelect = 2
        const val SingleSelect = 3
        const val SingleColorSelect = 4
        const val MultiSelectTags = 5

    }


}

