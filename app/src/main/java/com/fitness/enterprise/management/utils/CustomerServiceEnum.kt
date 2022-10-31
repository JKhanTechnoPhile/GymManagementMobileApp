package com.fitness.enterprise.management.utils

enum class CustomerServiceEnum {
    CUSTOMER_ENQUIRED {
        override fun getUserRoleAsStringForUi(): String {
            return "Customer Enquired"
        }

        override fun getUserRoleAsStringForServer(): String {
            return "CUSTOMER_ENQUIRED"
        }
    },
    CUSTOMER_REGISTERED {
        override fun getUserRoleAsStringForUi(): String {
            return "Customer Registered"
        }

        override fun getUserRoleAsStringForServer(): String {
            return "CUSTOMER_REGISTERED"
        }
    },
    CUSTOMER_PLAN_ACTIVE {
        override fun getUserRoleAsStringForUi(): String {
            return "Subscription Plan Active"
        }

        override fun getUserRoleAsStringForServer(): String {
            return "CUSTOMER_PLAN_ACTIVE"
        }
    },
    CUSTOMER_PLAN_INACTIVE {
        override fun getUserRoleAsStringForUi(): String {
            return "Subscription Plan InActive"
        }

        override fun getUserRoleAsStringForServer(): String {
            return "CUSTOMER_PLAN_INACTIVE"
        }
    };

    abstract fun getUserRoleAsStringForUi(): String
    abstract fun getUserRoleAsStringForServer(): String
}