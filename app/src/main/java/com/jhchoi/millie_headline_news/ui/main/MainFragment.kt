package com.jhchoi.millie_headline_news.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhchoi.millie_headline_news.R
import com.jhchoi.millie_headline_news.databinding.FragmentMainBinding
import com.jhchoi.millie_headline_news.network.News
import com.jhchoi.millie_headline_news.network.repository.NewsRepository
import com.jhchoi.millie_headline_news.ui.main.adapter.NewsAdapter

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    private var isInitialized = false

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        if (savedInstanceState == null) {
            init()
        }
    }

    private fun init() {
        val newsList = ArrayList<News>()

        binding.rvNews.adapter = NewsAdapter(newsList) {
            MainFragmentDirections.actionMainFragmentToCustomWebView(url = it).apply {
                findNavController().navigate(this)
            }
        }

        when (resources.configuration.screenWidthDp >= 600) {
            true ->
                binding.rvNews.layoutManager = GridLayoutManager(context, 3)
            else ->
                binding.rvNews.layoutManager = LinearLayoutManager(context)
        }

        val newsRepository = NewsRepository(requireContext())
        val adapter : NewsAdapter = binding.rvNews.adapter as NewsAdapter

        if (isNetworkAvailable(requireContext()) ) {
            viewModel.getHeadlineNews(getString(R.string.api_key), newsRepository)
        } else {
            // Load cached data
            val cachedData = newsRepository.getCachedNewsData()
            if (cachedData != null) {
                adapter.setData(cachedData)
            } else {
                // Handle no network and no cached data scenario
            }
        }
        observe()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }



    private fun observe() {
        viewModel.resHeadline.observe(this) {
            it?.let {
                val adapter : NewsAdapter = binding.rvNews.adapter as NewsAdapter
                adapter.setData(it)
            }

        }
    }
}