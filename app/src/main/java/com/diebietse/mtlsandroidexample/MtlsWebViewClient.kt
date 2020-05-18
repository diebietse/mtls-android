package com.diebietse.mtlsandroidexample

import android.app.Activity
import android.net.http.SslError
import android.security.KeyChain
import android.util.Log
import android.webkit.ClientCertRequest
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient

class MtlsWebViewClient(
    private val activity: Activity,
    private val trustAll: Boolean = false
) : WebViewClient() {
    override fun onReceivedClientCertRequest(view: WebView, request: ClientCertRequest) {
        KeyChain.choosePrivateKeyAlias(activity, { alias ->
            if (alias == null) {
                super.onReceivedClientCertRequest(view, request)
                return@choosePrivateKeyAlias
            }
            try {
                val certChain = KeyChain.getCertificateChain(activity, alias)
                val privateKey = KeyChain.getPrivateKey(activity, alias)
                if (certChain == null || privateKey == null) {
                    super.onReceivedClientCertRequest(view, request)
                } else {
                    request.proceed(privateKey, certChain)
                }
            } catch (e: Exception) {
                Log.e(
                    "MtlsWebViewClient",
                    "Error when getting CertificateChain or PrivateKey for alias '${alias}'",
                    e
                )
                super.onReceivedClientCertRequest(view, request)
            }

        }, request.keyTypes, request.principals, request.host, request.port, null)
    }

    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        if (trustAll) handler.proceed() else super.onReceivedSslError(view, handler, error)
    }
}