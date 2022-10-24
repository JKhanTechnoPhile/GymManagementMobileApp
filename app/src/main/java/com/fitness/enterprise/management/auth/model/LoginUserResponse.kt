package com.fitness.enterprise.management.auth.model

data class LoginUserResponse(
    val token: String
) {
    var userName: String? = null
    var roleType: Int? = null
}