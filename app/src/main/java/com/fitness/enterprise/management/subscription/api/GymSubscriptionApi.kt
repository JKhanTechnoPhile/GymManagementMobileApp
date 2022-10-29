package com.fitness.enterprise.management.subscription.api

import com.fitness.enterprise.management.subscription.model.DeleteGymSubscriptionResponse
import com.fitness.enterprise.management.subscription.model.GymSubscription
import com.fitness.enterprise.management.subscription.model.GymSubscriptionCreateRequest
import com.fitness.enterprise.management.subscription.model.GymSubscriptionResponse
import retrofit2.Response
import retrofit2.http.*

interface GymSubscriptionApi {
    @GET("/api/subscriptions/")
    suspend fun getAllGymSubscriptions() : Response<GymSubscriptionResponse>

    @GET("/api/branches/{gymCode}")
    suspend fun getAllGymSubscriptionsByGymCode(@Path("gymCode") gymCode: Int) : Response<GymSubscriptionResponse>

    @GET("/api/branches/{gymCode}/{gymSubscriptionPlanCode}")
    suspend fun getGymSubscriptionDetails(@Path("gymCode") gymCode: Int, @Path("gymSubscriptionPlanCode") gymSubscriptionPlanCode: Int) : Response<GymSubscription>

    @POST("/api/subscriptions/")
    suspend fun createGymSubscription(@Body createGymSubscriptionRequest: GymSubscriptionCreateRequest) : Response<GymSubscription>

    @PUT("/api/branches/{gymCode}/{gymSubscriptionPlanCode}")
    suspend fun updateGymSubscription(@Path("gymCode") gymCode: Int, @Path("gymSubscriptionPlanCode") gymSubscriptionPlanCode: Int, @Body updateGymSubscriptionRequest: GymSubscription) : Response<GymSubscription>

    @DELETE("/api/branches/{gymCode}/{gymSubscriptionPlanCode}")
    suspend fun deleteGymSubscription(@Path("gymCode") gymCode: Int, @Path("gymSubscriptionPlanCode") gymSubscriptionPlanCode: Int) : Response<DeleteGymSubscriptionResponse>
}