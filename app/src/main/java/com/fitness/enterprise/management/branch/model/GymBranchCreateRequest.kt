package com.fitness.enterprise.management.branch.model

data class GymBranchCreateRequest(
    val gymContact: String,
    val gymFullAddress: String,
    val gymLocationLat: String,
    val gymLocationLong: String,
    val gymName: String
)