package com.fitness.enterprise.management.auth.api

import com.fitness.enterprise.management.auth.model.LoginUserRequest
import com.fitness.enterprise.management.auth.model.LoginUserResponse
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.auth.model.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthUserApi {
    @POST("/api/v1/auth/register")
    suspend fun registerUser(@Body userRequest: RegisterUserRequest) : Response<RegisterUserResponse>

    @POST("/api/v1/auth/login")
    suspend fun loginUser(@Body userRequest: LoginUserRequest) : Response<LoginUserResponse>
}