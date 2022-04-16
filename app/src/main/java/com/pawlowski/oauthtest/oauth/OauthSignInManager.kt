package com.pawlowski.oauthtest.oauth

import android.util.Log
import com.pawlowski.oauthtest.oauth.networking.FetchTokenUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OauthSignInManager(private val fetchTokenUseCase: FetchTokenUseCase, private val sessionManager: SessionManager): OauthWebViewClient.CodeListener {

    private var fetchingTokenJob: Job? = null

    private var signInActionsListener: SignInActionsListener? = null

    override fun onSignInComplete(codeResult: OauthWebViewClient.CodeResult) {
        if(codeResult is OauthWebViewClient.CodeResult.Success)
        {
            val code = codeResult.code
            signInActionsListener?.onAuthCodeSuccess()
            startFetchingToken(code)
        }
        else
        {
            signInActionsListener?.onSignInError()
        }
    }

    private fun startFetchingToken(authCode: String)
    {
        fetchingTokenJob = CoroutineScope(Dispatchers.Main).launch {
            val tokenResult = fetchTokenUseCase.fetchToken(authCode)
            if(tokenResult is FetchTokenUseCase.TokenResult.Success)
            {
                Log.d("OAuth", "Here is the access token! ${tokenResult.token}")
                sessionManager.saveRefreshToken(tokenResult.token.refreshToken)

                signInActionsListener?.onTokenSuccess()
            }
            else
            {
                signInActionsListener?.onSignInError()
            }
        }
    }

    fun setSignInActionsListener(signInActionsListener: SignInActionsListener)
    {
        this.signInActionsListener = signInActionsListener
    }



    interface SignInActionsListener
    {
        fun onAuthCodeSuccess()
        fun onTokenSuccess()
        fun onSignInError()
    }
}