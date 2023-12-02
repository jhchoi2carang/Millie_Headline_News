package com.jhchoi.millie_headline_news.network

data class Headline(
    val status: String,
    val totalResult: Int,
    val articles : ArrayList<News>,
)
data class News(
    val urlToImage: String,
    val title: String,
    val publishedAt: String,
    val url: String,
)
