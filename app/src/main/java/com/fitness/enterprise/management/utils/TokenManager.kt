package com.fitness.enterprise.management.utils

import android.content.Context
import com.fitness.enterprise.management.auth.model.LoginUserResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {

    private var prefs = context.getSharedPreferences(Constants.PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveSessionData(loginUserResponse: LoginUserResponse) {
        val editor = prefs.edit()
        editor.putString(Constants.USER_TOKEN, loginUserResponse.token)
        loginUserResponse.userName?.let {
            editor.putString(Constants.USER_NAME_LOGGED_IN, it)
        }
        loginUserResponse.roleType?.let {
            editor.putInt(Constants.USER_TYPE_LOGGED_IN, it)
        }
        editor.apply()
    }

    fun getLoggedInToken() : String? {
        return prefs.getString(Constants.USER_TOKEN, null)
    }

    fun getLoggedInUserName() : String? {
        return prefs.getString(Constants.USER_NAME_LOGGED_IN, null)
    }

    fun getLoggedInUserType() : Int {
        return prefs.getInt(Constants.USER_TYPE_LOGGED_IN, Constants.DEFAULT_VALUE)
    }
}