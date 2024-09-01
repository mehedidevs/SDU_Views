package com.example.sdu_android_views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sdu_android_views.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ServerDrivenUIAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ServerDrivenUIAdapter()
        binding.recyclerView.adapter = adapter

        val jsonData = loadJSONFromAsset("product_filter.json")
        parseAndCreateUI(jsonData)
    }

    private fun loadJSONFromAsset(fileName: String): String {
        return assets.open(fileName).bufferedReader().use { it.readText() }
    }

    private fun parseAndCreateUI(jsonData: String) {
        val json = JSONObject(jsonData)
        val sections = json.getJSONArray("sections")
        val uiElements = mutableListOf<UIElement>()

        for (i in 0 until sections.length()) {
            val section = sections.getJSONObject(i)
            val type = section.getString("type")
            val title = section.getString("title")
            val content = section.optJSONArray("content")

            when (type) {
                "multi_select" -> uiElements.add(UIElement.MultiSelect(title, content))
                "price_range" -> uiElements.add(
                    UIElement.PriceRange(
                        title,
                        section.getJSONObject("range_data")
                    )
                )

                "multi_color_select" -> uiElements.add(UIElement.MultiColorSelect(title, content))
                "single_select" -> uiElements.add(UIElement.SingleSelect(title, content))
                "single_color_select" -> uiElements.add(UIElement.SingleColorSelect(title, content))
                "multi_select_tags" -> uiElements.add(UIElement.MultiSelectTags(title, content))
            }
        }

        adapter.submitList(uiElements)
    }
}