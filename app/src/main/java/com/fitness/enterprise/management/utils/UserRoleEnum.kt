package com.fitness.enterprise.management.utils

enum class UserRoleEnum {
    ROLE_PLATFORM_ADMIN {
        override fun getUserRoleAsStringForUi(): String {
            return "Platform Admin"
        }

        override fun getUserRoleAsStringForServer(): String {
            return "ROLE_PLATFORM_ADMIN"
        }

        override fun getUserRoleAsCode(): Int {
            return 101
        }
    },
    ROLE_BRANCH_ADMIN {
        override fun getUserRoleAsStringForUi(): String {
            return "Branch Admin"
        }

        override fun getUserRoleAsStringForServer(): String {
            return "ROLE_BRANCH_ADMIN"
        }

        override fun getUserRoleAsCode(): Int {
            return 102
        }
    },
    ROLE_BRANCH_MANAGER {
        override fun getUserRoleAsStringForUi(): String {
            return "Branch Manager"
        }

        override fun getUserRoleAsStringForServer(): String {
            return "ROLE_BRANCH_MANAGER"
        }

        override fun getUserRoleAsCode(): Int {
            return 103
        }
    };

    abstract fun getUserRoleAsStringForUi(): String
    abstract fun getUserRoleAsStringForServer(): String
    abstract fun getUserRoleAsCode(): Int
}