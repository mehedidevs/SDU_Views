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
            is UIElement.MultiSelect -> MULTI_SELECT
            is UIElement.PriceRange -> PRICE_RANGE
            is UIElement.MultiColorSelect -> MULTI_COLOR_SELECT
            is UIElement.SingleSelect -> SINGLE_SELECT
            is UIElement.SingleColorSelect -> SINGLE_COLOR_SELECT
            is UIElement.MultiSelectTags -> MULTI_SELECT_TAGS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MULTI_SELECT -> MultiSelectViewHolder(
                ItemMultiSelectBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            PRICE_RANGE -> RangeViewHolder(
                ItemPriceRangeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            MULTI_COLOR_SELECT -> MultiColorSelectViewHolder(
                ItemMultiColorSelectBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            SINGLE_SELECT -> SingleSelectViewHolder(
                ItemSingleSelectBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            SINGLE_COLOR_SELECT -> SingleColorSelectViewHolder(
                ItemSingleColorSelectBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            MULTI_SELECT_TAGS -> MultiSelectTagsViewHolder(
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
        const val MULTI_SELECT = 0
        const val PRICE_RANGE = 1
        const val MULTI_COLOR_SELECT = 2
        const val SINGLE_SELECT = 3
        const val SINGLE_COLOR_SELECT = 4
        const val MULTI_SELECT_TAGS = 5
    }


}

