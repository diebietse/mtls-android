package com.diebietse.mtlsandroidexample

import android.content.Context
import android.security.KeyChain
import android.security.KeyChainException
import java.security.KeyStore
import java.util.*
import javax.net.ssl.*

class MtlsHelper internal constructor(context: Context, alias: String, trustAll: Boolean = false) {
    val trustManager: X509TrustManager
    val sslSocketFactory: SSLSocketFactory

    init {
        val trustManagers = if (trustAll) arrayOf(TrustAllTrustManager()) else systemTrustManagers()
        trustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(arrayOf(keyManagerFromAlias(context, alias)), trustManagers, null)
        sslSocketFactory = sslContext.socketFactory
    }

    class CertificateNotFoundException(message: String?) : Exception(message)
    class PrivateKeyNotFoundException(message: String?) : Exception(message)

    @Throws(
        InterruptedException::class,
        KeyChainException::class,
        CertificateNotFoundException::class,
        PrivateKeyNotFoundException::class
    )
    private fun keyManagerFromAlias(context: Context, alias: String): KeyManager {
        val certChain = KeyChain.getCertificateChain(context, alias)
            ?: throw PrivateKeyNotFoundException("PrivateKey for alias '${alias}' not found")
        val privateKey = KeyChain.getPrivateKey(context, alias)
            ?: throw CertificateNotFoundException("Certificate for alias '${alias}' not found")
        return ClientCertKeyManager(alias, certChain, privateKey)
    }

    private fun systemTrustManagers(): Array<TrustManager> {
        val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        factory.init(null as KeyStore?)
        val trustManagers = factory.trustManagers
        if (trustManagers.size != 1 || (trustManagers[0] !is X509TrustManager)) {
            throw IllegalStateException(
                "Unexpected default trust managers: ${Arrays.toString(trustManagers)}"
            )
        }

        return factory.trustManagers
    }
}