package com.fitness.enterprise.management.utils

enum class GymSubscriptionEnum {
    GYM_ONE_TIME_REGISTRATION {
        override fun getGymSubscriptionPlanAsString(): String {
            return "One time registration"
        }

        override fun getGymSubscriptionPlanAsCode(): String {
            return "GYM_ONE_TIME_REGISTRATION"
        }
    },
    GYM_PLAN_FREQUENCY_ONE_MONTH {
        override fun getGymSubscriptionPlanAsString(): String {
            return "One month subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): String {
            return "GYM_PLAN_FREQUENCY_ONE_MONTH"
        }
    },
    GYM_PLAN_FREQUENCY_TWO_MONTHS {
        override fun getGymSubscriptionPlanAsString(): String {
            return "Two month subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): String {
            return "GYM_PLAN_FREQUENCY_TWO_MONTHS"
        }
    },
    GYM_PLAN_FREQUENCY_QUARTERLY {
        override fun getGymSubscriptionPlanAsString(): String {
            return "Quarterly subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): String {
            return "GYM_PLAN_FREQUENCY_QUARTERLY"
        }
    },
    GYM_PLAN_FREQUENCY_HALF_YEARLY {
        override fun getGymSubscriptionPlanAsString(): String {
            return "Half yearly subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): String {
            return "GYM_PLAN_FREQUENCY_HALF_YEARLY"
        }
    },
    GYM_PLAN_FREQUENCY_YEARLY {
        override fun getGymSubscriptionPlanAsString(): String {
            return "Yearly subscription plan"
        }

        override fun getGymSubscriptionPlanAsCode(): String {
            return "GYM_PLAN_FREQUENCY_YEARLY"
        }
    };

    abstract fun getGymSubscriptionPlanAsString(): String
    abstract fun getGymSubscriptionPlanAsCode(): String
}