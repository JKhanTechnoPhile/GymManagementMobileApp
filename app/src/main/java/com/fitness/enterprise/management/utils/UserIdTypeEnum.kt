package com.fitness.enterprise.management.utils

enum class UserIdTypeEnum {
    USER_ID_TYPE_AADHAAR {
        override fun getUserIdAsString(): String {
            return "Aadhaar"
        }

        override fun getUserIdAsCode(): Int {
            return 101
        }
    },
    USER_ID_TYPE_PAN {
        override fun getUserIdAsString(): String {
            return "PAN"
        }

        override fun getUserIdAsCode(): Int {
            return 102
        }
    },
    USER_ID_TYPE_DRIVING_LICENSE {
        override fun getUserIdAsString(): String {
            return "Driving License"
        }

        override fun getUserIdAsCode(): Int {
            return 103
        }
    },
    USER_ID_TYPE_VOTER_ID {
        override fun getUserIdAsString(): String {
            return "Voter Id"
        }

        override fun getUserIdAsCode(): Int {
            return 104
        }
    },
    USER_ID_TYPE_PASSPORT {
        override fun getUserIdAsString(): String {
            return "Passport"
        }

        override fun getUserIdAsCode(): Int {
            return 105
        }
    };

    abstract fun getUserIdAsString(): String
    abstract fun getUserIdAsCode(): Int
}