package com.diebietse.mtlsandroidexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.security.KeyChain
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class OkHttpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ok_http, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultTextView = view.findViewById<TextView>(R.id.text_view_result)
        val cardView = view.findViewById<CardView>(R.id.card_view)
        KeyChain.choosePrivateKeyAlias(requireActivity(), { alias ->
            val httpClient = OkHttpClient.Builder().apply {
                alias?.let {
                    val helper =
                        MtlsHelper(requireContext(), alias, trustAll = BuildConfig.SKIP_TLS_VERIFY)
                    sslSocketFactory(helper.sslSocketFactory, helper.trustManager)
                }
            }.build()

            val request =
                Request.Builder().url(BuildConfig.OK_HTTP_URL).build()
            val response = httpClient.newCall(request).execute()
            val responseBody = response.body ?: throw IOException("Response body is null")
            val result = responseBody.string()
            view.post {
                cardView.visibility = View.VISIBLE
                resultTextView.text = result
            }

            val clientCertificatePath = JSONObject(result).getString("client_certificate_path")
            val clientCertificateUrl = BuildConfig.OK_HTTP_URL.toHttpUrl()
                .newBuilder()
                .encodedPath(clientCertificatePath)
                .toString()
            val button = view.findViewById<Button>(R.id.button_download_cert)

            button.visibility = Button.VISIBLE
            button.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(clientCertificateUrl) }
                startActivity(intent)
            }
        }, null, null, null, null)

    }
}
