package com.jhchoi.millie_headline_news.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jhchoi.millie_headline_news.network.ApiClient
import com.jhchoi.millie_headline_news.network.Headline
import com.jhchoi.millie_headline_news.network.News
import com.jhchoi.millie_headline_news.network.repository.NewsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val LOG_TAG = MainViewModel::class.simpleName
    val resHeadline = SingleLiveEvent<List<News>?>()


    fun getHeadlineNews(api_key: String, repository: NewsRepository) {

        val call: Call<Headline> = ApiClient.apiService.getHeadline(api_key)
        call.enqueue(object : Callback<Headline> {
            override fun onResponse(call: Call<Headline>, response: Response<Headline>) {
                if (response.isSuccessful) {
                    val headlineList = response.body()
                    Log.d("LOG_TAG", headlineList.toString())
                    if (headlineList != null) {
                        resHeadline.postValue(headlineList.articles)
                        repository.saveNewsData(headlineList.articles)
                    }
                } else {
                    // 오류 응답 처리
                }
            }

            override fun onFailure(call: Call<Headline>, t: Throwable) {
                // 네트워크 오류 등 예외 처리
                Log.e(LOG_TAG, t.toString())
            }
        })

    }
}


