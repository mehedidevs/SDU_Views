package com.example.sdu_android_views

import android.graphics.Color
import androidx.recyclerview.widget.ListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.sdu_android_views.databinding.ItemSingleSelectionBinding

/**
Created by Masum Mehedi on 9/2/2024.
masumehedissl@gmail.com
 **/

class ColorMultiSelectionAdapter :
    ListAdapter<ColorItem, ColorMultiSelectionAdapter.ViewHolder>(ColorDiffCallback()) {

    private val selectedColors = mutableSetOf<String>()
    private var onColorSelectedListener: ((Set<String>) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemSingleSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(colorItem: ColorItem) {
            val color = colorItem.color.toValidColor()
            binding.colorView.setBackgroundColor(colorItem.color.toValidColor())
            binding.checkIcon.visibility = if (colorItem.isSelected) View.VISIBLE else View.GONE

            if (colorItem.isSelected) {
                val luminance = ColorUtils.calculateLuminance(color)
                binding.checkIcon.setColorFilter(if (luminance > 0.5) Color.BLACK else Color.WHITE)
            }

            itemView.setOnClickListener {
                toggleColorSelection(colorItem)
                onColorSelectedListener?.invoke(selectedColors)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSingleSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun toggleColorSelection(colorItem: ColorItem) {
        colorItem.isSelected = !colorItem.isSelected
        if (colorItem.isSelected) {
            selectedColors.add(colorItem.color)
        } else {
            selectedColors.remove(colorItem.color)
        }
        notifyItemChanged(currentList.indexOf(colorItem))
    }

    fun setOnColorSelectedListener(listener: (Set<String>) -> Unit) {
        onColorSelectedListener = listener
    }

}

