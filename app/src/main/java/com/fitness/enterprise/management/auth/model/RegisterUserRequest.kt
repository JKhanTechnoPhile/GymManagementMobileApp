package com.fitness.enterprise.management.auth.model

data class RegisterUserRequest(
    val emailId: String,
    val gymBranchCode: String,
    val id: Int,
    val password: String,
    val phoneNumber: String,
    val roleType: Int,
    val userCreatedDate: String,
    val userIdProof: String,
    val userIdType: String,
    val userName: String,
    val userType: String
)