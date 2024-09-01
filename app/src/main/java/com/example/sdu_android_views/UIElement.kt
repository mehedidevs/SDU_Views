package com.example.sdu_android_views

import org.json.JSONArray
import org.json.JSONObject

/**
Created by Masum Mehedi on 9/1/2024.
masumehedissl@gmail.com
 **/
sealed class UIElement {
    data class MultiSelect(val title: String, val options: JSONArray?) : UIElement()
    data class PriceRange(val title: String, val rangeData: JSONObject) : UIElement()
    data class MultiColorSelect(val title: String, val options: JSONArray?) : UIElement()
    data class SingleSelect(val title: String, val options: JSONArray?) : UIElement()
    data class SingleColorSelect(val title: String, val options: JSONArray?) : UIElement()
    data class MultiSelectTags(val title: String, val options: JSONArray?) : UIElement()
}