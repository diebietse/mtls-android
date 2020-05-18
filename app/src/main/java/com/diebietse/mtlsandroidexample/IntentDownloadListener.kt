package com.diebietse.mtlsandroidexample

import android.content.Intent
import android.net.Uri
import android.webkit.DownloadListener
import androidx.fragment.app.Fragment

class IntentDownloadListener(private val fragment: Fragment) : DownloadListener {
    override fun onDownloadStart(
        url: String,
        userAgent: String,
        contentDisposition: String,
        mimetype: String,
        contentLength: Long
    ) {
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }
        fragment.startActivity(intent)
    }
}