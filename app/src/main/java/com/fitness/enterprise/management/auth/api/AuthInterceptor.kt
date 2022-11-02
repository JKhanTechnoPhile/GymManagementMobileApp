package com.fitness.enterprise.management.auth.api

import com.fitness.enterprise.management.auth.di.SessionManager
import com.fitness.enterprise.management.utils.UserRoleEnum
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        sessionManager.loginUserLiveData.value?.token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }

        sessionManager.loginUserLiveData.value?.loggedInUser?.userType?.let {
            request.addHeader("LoggedInUserType", "${UserRoleEnum.valueOf(it).getUserRoleAsCode()}")
        }

        return chain.proceed(request.build())
    }
}