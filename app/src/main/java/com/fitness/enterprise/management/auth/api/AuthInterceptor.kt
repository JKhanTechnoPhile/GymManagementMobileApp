package com.fitness.enterprise.management.auth.api

import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = tokenManager.getLoggedInToken()
        request.addHeader("Authorization", "Bearer $token")

        val userType = tokenManager.getLoggedInUserType()
        if (userType != Constants.DEFAULT_VALUE) {
            request.addHeader("LoggedInUserType", "$userType")
        }

        return chain.proceed(request.build())
    }
}