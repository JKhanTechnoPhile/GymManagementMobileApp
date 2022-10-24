package com.fitness.enterprise.management.subscription.model

data class GymSubscriptionCreateRequest(
    val gymSubscriptionName: String,
    val gymSubscriptionFrequency: String,
    val gymSubscriptionBaseFare: String,
    val gymSubscriptionStartDate: String,
    val gymSubscriptionEndDate: String,
    val gymBranchCode: String
)