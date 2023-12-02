package com.jhchoi.millie_headline_news.network.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jhchoi.millie_headline_news.network.News

class NewsRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("news_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveNewsData(data: List<News>) {
        val json = gson.toJson(data)
        sharedPreferences.edit().putString("cached_news", json).apply()
    }

    fun getCachedNewsData(): List<News>? {
        val json = sharedPreferences.getString("cached_news", null)
        return if (json != null) {
            val type = object : TypeToken<List<News>>() {}.type
            gson.fromJson(json, type)
        } else {
            null
        }
    }
}

