package com.example.sdu_android_views

/**
Created by Masum Mehedi on 9/2/2024.
masumehedissl@gmail.com
 **/
import android.graphics.Color

/**
 * Extension function to validate and extract color code from a string.
 * Returns the parsed color if valid; otherwise, returns a default color.
 */
fun String.toValidColor(defaultColor: Int = Color.BLACK): Int {
    return try {
        if (this.isValidColorCode()) {
            Color.parseColor(this)
        } else {
            defaultColor
        }
    } catch (e: IllegalArgumentException) {
        defaultColor
    }
}

/**
 * Extension function to check if a string is a valid color code.
 */
fun String.isValidColorCode(): Boolean {
    // Regex pattern to match valid color codes: #RGB, #ARGB, #RRGGBB, #AARRGGBB
    val colorCodePattern = Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8}|[A-Fa-f0-9]{3}|[A-Fa-f0-9]{4})$")
    return this.matches(colorCodePattern)
}