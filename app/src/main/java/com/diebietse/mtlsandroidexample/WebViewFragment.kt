package com.diebietse.mtlsandroidexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment

class WebViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = view.findViewById<WebView>(R.id.web_view)
        webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = MtlsWebViewClient(
                this@WebViewFragment.requireActivity(),
                trustAll = BuildConfig.SKIP_TLS_VERIFY
            )
            loadUrl(BuildConfig.WEB_VIEW_URL)
            setDownloadListener(IntentDownloadListener(this@WebViewFragment))
        }
    }
}
