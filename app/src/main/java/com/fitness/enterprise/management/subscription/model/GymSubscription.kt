package com.fitness.enterprise.management.subscription.model

import com.fitness.enterprise.management.branch.model.GymBranch

data class GymSubscription(
    val gymSubscriptionId: Int,
    val gymSubscriptionName: String,
    val gymSubscriptionFrequency: String,
    val gymSubscriptionBaseFare: String,
    val gymSubscriptionStartDate: String,
    val gymSubscriptionEndDate: String,
    val gymBranchCode: String,
    val gymBranch: GymBranch
)