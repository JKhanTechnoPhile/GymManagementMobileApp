package com.fitness.enterprise.management.common.api.gym.branch

import com.fitness.enterprise.management.common.api.gym.branch.model.GymBranch
import com.fitness.enterprise.management.common.api.gym.branch.model.GymBranchesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GymBranchApi {
    @GET("/api/branches/")
    suspend fun getAllGymBranches() : Response<GymBranchesResponse>

    @GET("/api/branches/{gymCode}")
    suspend fun getGymBranchDetails(@Path("gymCode") gymCode: Int) : Response<GymBranch>
}