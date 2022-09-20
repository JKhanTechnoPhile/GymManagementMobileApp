package com.fitness.enterprise.management.auth.model

import com.fitness.enterprise.management.common.model.GymBranch
import com.fitness.enterprise.management.common.model.Role

data class RegisterUserResponse(
    val emailId: String,
    val gymBranch: GymBranch,
    val gymBranchCode: String,
    val id: Int,
    val password: String,
    val phoneNumber: String,
    val roleType: Int,
    val roles: List<Role>,
    val userCreatedDate: String,
    val userIdProof: String,
    val userIdType: String,
    val userName: String,
    val userType: String
)