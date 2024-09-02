package com.example.sdu_android_views

import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class ColorSelectionAdapter :
    ListAdapter<ColorItem, ColorSelectionAdapter.ViewHolder>(ColorDiffCallback()) {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var onColorSelectedListener: ((String) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorView: View = itemView.findViewById(R.id.colorView)
        val checkIcon: ImageView = itemView.findViewById(R.id.checkIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_single_selection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorItem = getItem(position)


        val color = colorItem.color.toValidColor()
        holder.colorView.setBackgroundColor(color)

        holder.checkIcon.visibility =
            if (position == selectedPosition) View.VISIBLE else View.GONE

        if (position == selectedPosition) {
            val luminance = ColorUtils.calculateLuminance(color)
            holder.checkIcon.setColorFilter(if (luminance > 0.5) Color.BLACK else Color.WHITE)
        }

        holder.itemView.setOnClickListener {
            setSelectedPosition(position)
            onColorSelectedListener?.invoke(colorItem.color)
        }


    }

    private fun setSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

    fun setOnColorSelectedListener(listener: (String) -> Unit) {
        onColorSelectedListener = listener
    }
}

class ColorDiffCallback : DiffUtil.ItemCallback<ColorItem>() {
    override fun areItemsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean {
        return oldItem.color == newItem.color
    }

    override fun areContentsTheSame(oldItem: ColorItem, newItem: ColorItem): Boolean {
        return oldItem == newItem
    }
}