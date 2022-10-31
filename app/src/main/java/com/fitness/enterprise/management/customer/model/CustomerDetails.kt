package com.fitness.enterprise.management.customer.model

data class CustomerDetails(
    val customerName: String,
    val customerPhoneNumber: String,
    val gymBranchCode: String? = null,
    val gymSubscriptionPlanCode: Int? = null,
    val customerStatus: String,
    val id: Int? = null,
    val customerEmailId: String? = null,
    val customerIdType: String? = null,
    val customerIdProof: String? = null,
    val customerEnquiredDate: Long? = null,
    val customerRegisteredDate: Long? = null,
    val customerPlanActivatedDate: Long? = null
)