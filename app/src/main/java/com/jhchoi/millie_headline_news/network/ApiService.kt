package com.jhchoi.millie_headline_news.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=kr") // API 엔드포인트 경로
    fun getHeadline(@Query("apiKey") api_key : String): Call<Headline> // Post 모델을 반환하는 메서드
}

