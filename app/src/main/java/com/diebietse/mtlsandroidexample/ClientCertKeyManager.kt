package com.diebietse.mtlsandroidexample

import java.net.Socket
import java.security.Principal
import java.security.PrivateKey
import java.security.cert.X509Certificate
import javax.net.ssl.X509KeyManager

class ClientCertKeyManager(
    private val alias: String,
    private val certChain: Array<X509Certificate>,
    private val privateKey: PrivateKey
) : X509KeyManager {
    override fun chooseClientAlias(
        keyType: Array<String>,
        issuers: Array<Principal>,
        socket: Socket
    ): String {
        return alias
    }

    override fun getCertificateChain(alias: String): Array<X509Certificate>? {
        return if (this.alias == alias) certChain else null
    }

    override fun getPrivateKey(alias: String): PrivateKey? {
        return if (this.alias == alias) privateKey else null
    }

    override fun chooseServerAlias(
        keyType: String,
        issuers: Array<Principal>,
        socket: Socket
    ): String {
        throw UnsupportedOperationException("chooseServerAlias")
    }

    override fun getClientAliases(
        keyType: String,
        issuers: Array<Principal>
    ): Array<String> {
        throw UnsupportedOperationException("getClientAliases")
    }

    override fun getServerAliases(
        keyType: String,
        issuers: Array<Principal>
    ): Array<String> {
        throw UnsupportedOperationException("getServerAliases")
    }

}