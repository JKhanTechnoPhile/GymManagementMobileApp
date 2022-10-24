package com.fitness.enterprise.management.utils

enum class GymSubscriptionEnum {
    GYM_ONE_TIME_REGISTRATION {
        override fun getGymSubscriptionPlanAsString(): String {
            return "One time registration"
        }

        override fun getGymSubscriptionPlanAsCode(): Int {
            return 101
        }
    },
    GYM_PLAN_FREQUENCY_ONE_MONTH {
        override fun getGymSubscriptionPlanAsString(): String {
            return "One month subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): Int {
            return 102
        }
    },
    GYM_PLAN_FREQUENCY_TWO_MONTHS {
        override fun getGymSubscriptionPlanAsString(): String {
            return "Two month subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): Int {
            return 103
        }
    },
    GYM_PLAN_FREQUENCY_QUARTERLY {
        override fun getGymSubscriptionPlanAsString(): String {
            return "Quarterly subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): Int {
            return 104
        }
    },
    GYM_PLAN_FREQUENCY_HALF_YEARLY {
        override fun getGymSubscriptionPlanAsString(): String {
            return "Half yearly subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): Int {
            return 105
        }
    },
    GYM_PLAN_FREQUENCY_YEARLY {
        override fun getGymSubscriptionPlanAsString(): String {
            return "Yearly subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): Int {
            return 106
        }
    };

    abstract fun getGymSubscriptionPlanAsString(): String
    abstract fun getGymSubscriptionPlanAsCode(): Int
}