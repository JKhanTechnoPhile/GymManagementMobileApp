package com.fitness.enterprise.management.auth.model

data class LoginUserRequest(
    val password: String,
    val username: String
)