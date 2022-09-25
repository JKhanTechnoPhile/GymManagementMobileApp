package com.fitness.enterprise.management.utils

enum class UserRoleEnum {
    ROLE_PLATFORM_ADMIN {
        override fun getUserRoleAsString(): String {
            return "Platform Admin"
        }

        override fun getUserRoleAsCode(): Int {
            return 101
        }
    },
    ROLE_BRANCH_ADMIN {
        override fun getUserRoleAsString(): String {
            return "Branch Admin"
        }

        override fun getUserRoleAsCode(): Int {
            return 102
        }
    },
    ROLE_BRANCH_MANAGER {
        override fun getUserRoleAsString(): String {
            return "Branch Manager"
        }

        override fun getUserRoleAsCode(): Int {
            return 103
        }
    };

    abstract fun getUserRoleAsString(): String
    abstract fun getUserRoleAsCode(): Int
}