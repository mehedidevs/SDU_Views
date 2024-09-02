package com.example.sdu_android_views

data class ColorItem(
    val color: String,
    val icon: String? = null,
    val id: String,
    val title: String,
    var isSelected: Boolean = false
)