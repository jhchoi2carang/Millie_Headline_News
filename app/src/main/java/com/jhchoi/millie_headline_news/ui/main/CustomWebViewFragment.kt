package com.jhchoi.millie_headline_news.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jhchoi.millie_headline_news.R
import com.jhchoi.millie_headline_news.databinding.FragmentCustomWebviewBinding

class CustomWebViewFragment : Fragment() {
    lateinit var binding: FragmentCustomWebviewBinding

    private lateinit var viewModel: CustomWebViewViewModel
    private val args: CustomWebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_webview, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomWebViewViewModel::class.java)
        init()
    }

    fun init() {

        val webSettings: WebSettings = binding.webview.settings
        webSettings.javaScriptEnabled = true

        // Set a WebChromeClient to handle progress and title changes (optional)
        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                // Handle progress changes
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                // Handle title changes
            }
        }

        binding.webview.loadUrl(args.url)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        binding.webview.destroy()
        super.onDestroyView()

    }


}