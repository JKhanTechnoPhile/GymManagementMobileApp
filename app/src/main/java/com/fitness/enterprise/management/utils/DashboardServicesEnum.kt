package com.fitness.enterprise.management.utils

enum class DashboardServicesEnum {
    DASHBOARD_SERVICE_CUSTOMER {
        override fun getServiceName(): String {
            return "Customer Services"
        }

        override fun getServiceDescription(): String {
            return "Facilitate to register new or edit or view customer."
        }
    },
    DASHBOARD_SERVICE_GYM_BRANCH {
        override fun getServiceName(): String {
            return "Gym Branch Services"
        }

        override fun getServiceDescription(): String {
            return "Facilitate to register new or edit or view gym branch."
        }
    },
    DASHBOARD_SERVICE_GYM_SUBSCRIPTION {
        override fun getServiceName(): String {
            return "Gym Subscription Services"
        }

        override fun getServiceDescription(): String {
            return "Facilitate to add new or edit or view gym subscription plans."
        }
    },
    DASHBOARD_SERVICE_BRANCH_ADMIN {
        override fun getServiceName(): String {
            return "Branch Admin Services"
        }

        override fun getServiceDescription(): String {
            return "Facilitate to register new or edit or view branch admin."
        }
    };

    abstract fun getServiceName(): String
    abstract fun getServiceDescription(): String
}