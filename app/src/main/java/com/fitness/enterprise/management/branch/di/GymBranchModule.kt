package com.fitness.enterprise.management.branch.di

import com.fitness.enterprise.management.branch.api.GymBranchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GymBranchModule {

    @Singleton
    @Provides
    fun providesGymBranchApi(retrofitBuilder: Retrofit.Builder, client: OkHttpClient) : GymBranchApi {
        return retrofitBuilder.client(client).build().create(GymBranchApi::class.java)
    }
}