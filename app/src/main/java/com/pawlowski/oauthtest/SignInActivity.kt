package com.pawlowski.oauthtest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.pawlowski.oauthtest.di.ObjectFactory
import com.pawlowski.oauthtest.oauth.OauthSignInManager
import com.pawlowski.oauthtest.oauth.utils.OauthUtils
import java.util.*

class SignInActivity : AppCompatActivity(), OauthSignInManager.SignInActionsListener{
    private lateinit var webView: WebView
    private lateinit var oauthUtils: OauthUtils
    private lateinit var objectFactory: ObjectFactory
    private lateinit var oauthSignInManager: OauthSignInManager
    private lateinit var networkConnectionHelper: NetworkConnectionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.web_view)

        objectFactory = ObjectFactory(this)
        oauthUtils = objectFactory.oauthUtils
        oauthSignInManager = objectFactory.oauthSignInManager
        networkConnectionHelper = objectFactory.networkConnectionHelper

        if(networkConnectionHelper.isNetworkAvailable())
            initOauthSignIn()
        else
            showRetryDialog()
    }

    private fun showRetryDialog()
    {
        //TODO: Show dialog with button retry
    }

    private fun initOauthSignIn()
    {
        val randomState = UUID.randomUUID().toString()
        webView.webViewClient = objectFactory.oauthWebClient(randomState, oauthSignInManager)
        webView.loadUrl(oauthUtils.authUrl(randomState))
        webView.visibility = View.VISIBLE
    }

    override fun onAuthCodeSuccess() {
        Log.d("OAuth", "Auth code received!")
        webView.visibility = View.GONE
        //TODO: Show loading dialog
    }

    override fun onTokenSuccess() {
        //TODO: Change activity
    }

    override fun onSignInError() {
        Log.d("OAuth", "Auth failure")
        showRetryDialog()
    }


}