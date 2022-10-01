package com.fitness.enterprise.management.branch.api

import com.fitness.enterprise.management.branch.model.DeleteGymBranchResponse
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.branch.model.GymBranchCreateRequest
import com.fitness.enterprise.management.branch.model.GymBranchesResponse
import retrofit2.Response
import retrofit2.http.*

interface GymBranchApi {
    @GET("/api/branches/")
    suspend fun getAllGymBranches() : Response<GymBranchesResponse>

    @GET("/api/branches/{gymCode}")
    suspend fun getGymBranchDetails(@Path("gymCode") gymCode: Int) : Response<GymBranch>

    @POST("/api/branches/")
    suspend fun createGymBranch(@Body createGymBranchRequest: GymBranchCreateRequest) : Response<GymBranch>

    @PUT("/api/branches/{gymCode}")
    suspend fun updateGymBranch(@Path("gymCode") gymCode: Int, @Body updateGymBranchRequest: GymBranch) : Response<GymBranch>

    @DELETE("/api/branches/{gymCode}")
    suspend fun deleteGymBranch(@Path("gymCode") gymCode: Int) : Response<DeleteGymBranchResponse>
}