package com.fitness.enterprise.management.subscription.model

data class GymSubscriptionCreateRequest(
    val gymPlanName: String,
    val gymPlanFrequency: String,
    val gymPlanBaseFare: Float,
    val gymPlanCreatedDate: Long?,
    val gymPlanEndDate: Long?,
    val gymBranchCode: String
)