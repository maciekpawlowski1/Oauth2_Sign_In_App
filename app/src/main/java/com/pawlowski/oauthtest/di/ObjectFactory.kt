package com.pawlowski.oauthtest.di

import android.content.Context
import com.pawlowski.oauthtest.NetworkConnectionHelper
import com.pawlowski.oauthtest.oauth.OauthSignInManager
import com.pawlowski.oauthtest.oauth.OauthWebViewClient
import com.pawlowski.oauthtest.oauth.SessionManager
import com.pawlowski.oauthtest.oauth.networking.AuthService
import com.pawlowski.oauthtest.oauth.networking.FetchTokenUseCase
import com.pawlowski.oauthtest.oauth.utils.OauthUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ObjectFactory(private val context: Context) {

    val oauthUtils: OauthUtils by lazy { OauthUtils(context) }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(oauthUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val fetchTokenUseCase: FetchTokenUseCase get() {
        return FetchTokenUseCase(authService, oauthUtils)
    }

    fun oauthWebClient(state: String, codeListener: OauthWebViewClient.CodeListener): OauthWebViewClient
    {
        return OauthWebViewClient(oauthUtils.redirectUrl, state, codeListener)
    }

    val sessionManager: SessionManager by lazy {
        SessionManager(context)
    }

    val oauthSignInManager: OauthSignInManager by lazy {
        OauthSignInManager(fetchTokenUseCase, sessionManager)
    }

    val networkConnectionHelper: NetworkConnectionHelper by lazy {
        NetworkConnectionHelper(context)
    }
}