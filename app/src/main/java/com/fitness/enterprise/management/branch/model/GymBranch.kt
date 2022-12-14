package com.fitness.enterprise.management.branch.model

data class GymBranch(
    val gymBranchId: Int,
    val gymCode: String,
    val gymContact: String,
    val gymFullAddress: String,
    val gymLocationLat: String,
    val gymLocationLong: String,
    val gymName: String
)