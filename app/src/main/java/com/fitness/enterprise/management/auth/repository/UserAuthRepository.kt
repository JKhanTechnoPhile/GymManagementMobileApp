package com.fitness.enterprise.management.auth.repository

import android.util.Log
import com.fitness.enterprise.management.auth.api.AuthUserApi
import com.fitness.enterprise.management.auth.model.LoginUserRequest
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.utils.Constants.TAG
import javax.inject.Inject

class UserAuthRepository @Inject constructor(private val authUserApi: AuthUserApi) {

    suspend fun registerUser(registerUserRequest: RegisterUserRequest) {
        val registerUserResponse = authUserApi.registerUser(registerUserRequest)
        Log.d(TAG,  registerUserResponse.body().toString())
    }

    suspend fun loginUser(loginUserRequest: LoginUserRequest) {
        val loginUserResponse = authUserApi.loginUser(loginUserRequest)
        Log.d(TAG,  loginUserResponse.body().toString())
    }
}