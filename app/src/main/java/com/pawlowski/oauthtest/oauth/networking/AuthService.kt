package com.pawlowski.oauthtest.oauth.networking

import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @FormUrlEncoded
    @POST("/auth/realms/notesapp/protocol/openid-connect/token")
    suspend fun getToken(@Field("client_id")clientId: String,
                         @Field("client_secret") clientSecret: String,
                         @Field("code") authorizationCode: String,
                         @Field("grant_type") grantType: String,
                         @Field("redirect_uri") redirectUri: String,
                         @Field("scope") scope: String
                         ): Response<TokenResponse>
}