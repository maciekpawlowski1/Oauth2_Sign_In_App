package com.pawlowski.oauthtest.oauth

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SessionManager(private val context: Context) {

    //TODO: Change for encrypted Shared Preferences
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("SessionSharedPreferences", Context.MODE_PRIVATE)
    }

    @SuppressLint("ApplySharedPref")
    fun saveRefreshToken(refreshToken: String)
    {
        sharedPreferences.edit().putString("refresh_token", refreshToken).commit()
    }

    fun isRefreshTokenSaved(): Boolean = sharedPreferences.contains("refresh_token")

    fun getRefreshToken(): String = sharedPreferences.getString("refresh_token", "")!!


    @SuppressLint("ApplySharedPref")
    fun saveAccessToken(accessToken: String)
    {
        sharedPreferences.edit().putString("refresh_token", accessToken).commit()
    }

    fun getAccessToken(): String = sharedPreferences.getString("access_token", "")!!

}