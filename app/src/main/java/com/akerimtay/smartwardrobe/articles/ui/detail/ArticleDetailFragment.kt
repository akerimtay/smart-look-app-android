package com.akerimtay.smartwardrobe.articles.ui.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import com.akerimtay.smartwardrobe.databinding.FragmentArticleDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("SetJavaScriptEnabled")
class ArticleDetailFragment : BaseFragment(R.layout.fragment_article_detail) {
    private val binding: FragmentArticleDetailBinding by viewBinding()
    private val navArgs: ArticleDetailFragmentArgs by navArgs()
    private val viewModel: ArticleDetailViewModel by viewModel { parametersOf(navArgs.articleId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })
        with(binding) {
            toolbar.setNavigationOnClickListener { onBackPressed() }
            webView.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
            }
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    val url = request?.url.toString()
                    view?.loadUrl(url)
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    progressHorizontal.isVisible = true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    toolbar.title = view?.title
                    progressHorizontal.isVisible = false
                }
            }
        }
        viewModel.article.observeNotNull(viewLifecycleOwner) { article ->
            article?.let {
                binding.toolbar.title = it.name
                binding.webView.loadUrl(it.sourceUrl)
            }
        }
    }

    override fun onDestroyView() {
        binding.webView.stopLoading()
        super.onDestroyView()
    }

    private fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            findNavController().popBackStack()
        }
    }
}