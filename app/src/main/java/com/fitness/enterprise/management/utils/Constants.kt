package com.fitness.enterprise.management.utils

import java.text.DecimalFormat

object Constants {
    const val TAG = "Captain Fitness & Crossfit"
    const val BASE_URL = "http://192.168.1.9:9090"
    const val EMPTY_STRING = ""
    const val DEFAULT_VALUE = -1
    const val PREFS_TOKEN_FILE = "PREFS_TOKEN_FILE"
    const val USER_TOKEN = "USER_TOKEN"
    const val USER_NAME_LOGGED_IN = "USER_NAME_LOGGED_IN"
    const val USER_TYPE_LOGGED_IN = "LOGGED_IN_USER_TYPE"
    const val INR_CURRENCY = "₹"

    fun currencyFormat(amount: String): String {
        val formatter = DecimalFormat("###,###,##0.00")
        return formatter.format(amount.toDouble())
    }

    fun getCustomerStatus(customerStatus: String): String {
        return CustomerServiceEnum.valueOf(customerStatus).getUserRoleAsStringForUi()
    }
}