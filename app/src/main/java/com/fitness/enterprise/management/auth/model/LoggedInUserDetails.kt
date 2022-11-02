package com.fitness.enterprise.management.auth.model

import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.common.model.Role

data class LoggedInUserDetails(
    val id: Int,
    val userName: String,
    val phoneNumber: String,
    val emailId: String,
    val userIdType: String,
    val userIdProof: String,
    val userType: String,
    val roleType: Int,
    val gymBranchCode: String,
    val gymBranch: GymBranch,
    val password: String,
    val roles: List<Role>,
    val userCreatedDate: String
)