package com.pawlowski.oauthtest.oauth.networking

import com.pawlowski.oauthtest.oauth.utils.OauthUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchTokenUseCase(private val authService: AuthService, private val oauthUtils: OauthUtils) {

    suspend fun fetchToken(authCode: String): TokenResult
    {
        return withContext(Dispatchers.IO)
        {
            val response = authService.getToken(oauthUtils.clientId, oauthUtils.clientSecret, authCode,
                oauthUtils.grantType, oauthUtils.redirectUrl, oauthUtils.scope)

            return@withContext if(response.isSuccessful && response.body() != null)
            {
                TokenResult.Success(response.body()!!)
            }
            else
            {
                TokenResult.Failure
            }
        }

    }

    sealed class TokenResult {
        data class Success(val token: TokenResponse) : TokenResult()
        object Failure: TokenResult()
    }
}