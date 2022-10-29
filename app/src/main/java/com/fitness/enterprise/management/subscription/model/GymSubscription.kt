package com.fitness.enterprise.management.subscription.model

import com.fitness.enterprise.management.branch.model.GymBranch
import com.google.gson.annotations.SerializedName

data class GymSubscription(
    @SerializedName("id")
    val gymSubscriptionId: Int,
    @SerializedName("gymPlanName")
    val gymSubscriptionName: String,
    @SerializedName("gymPlanFrequency")
    val gymSubscriptionFrequency: String,
    @SerializedName("gymPlanBaseFare")
    val gymSubscriptionBaseFare: String,
    @SerializedName("gymPlanCreatedDate")
    val gymSubscriptionStartDate: String,
    @SerializedName("gymPlanEndDate")
    val gymSubscriptionEndDate: String,
    @SerializedName("gymBranchCode")
    val gymBranchCode: String,
    val gymBranch: GymBranch
)