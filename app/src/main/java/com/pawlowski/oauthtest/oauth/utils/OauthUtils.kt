package com.pawlowski.oauthtest.oauth.utils

import android.content.Context
import com.pawlowski.oauthtest.R

class OauthUtils(private val context: Context) {
    val clientId: String by lazy { context.getString(R.string.client_id) }
    val redirectUrl: String by lazy { context.getString(R.string.redirect_url) }
    val grantType: String by lazy { context.getString(R.string.grant_type) }
    val clientSecret: String by lazy { context.getString(R.string.client_secret) }
    val baseUrl : String by lazy { context.getString(R.string.base_url) }
    val scope: String by lazy {
        "openid profile"
    }
    private val realm: String by lazy {
        context.getString(R.string.realm)
    }
    fun authUrl(state: String): String
    {
        return "${baseUrl}/auth/realms/${realm}/protocol/openid-connect/auth?client_id=${clientId}&response_type=code&scope=${scope}&redirect_uri=${redirectUrl}&state=${state}"
    }
}