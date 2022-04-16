package com.pawlowski.oauthtest.oauth

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class OauthWebViewClient(private val redirectUrl:String, private val state: String, private val codeListener: CodeListener): WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        request?.let {
            if(request.url.toString().startsWith(redirectUrl))
            {
                val responseState = request.url.getQueryParameter("state")
                if (responseState == state) {
                    request.url.getQueryParameter("code")?.let { code ->
                        codeListener.onSignInComplete(CodeResult.Success(code))
                    } ?: run {
                        codeListener.onSignInComplete(CodeResult.Failure)
                    }
                }
            }
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    interface CodeListener
    {
        fun onSignInComplete(codeResult: CodeResult)
    }

    sealed class CodeResult {
        data class Success(val code: String) : CodeResult()
        object Failure: CodeResult()
    }
}