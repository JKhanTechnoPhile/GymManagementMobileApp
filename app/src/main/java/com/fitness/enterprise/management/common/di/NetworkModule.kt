package com.fitness.enterprise.management.common.di

import com.fitness.enterprise.management.auth.api.AuthUserApi
import com.fitness.enterprise.management.common.api.gym.branch.GymBranchApi
import com.fitness.enterprise.management.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthUserApi(retrofit: Retrofit) : AuthUserApi {
        return retrofit.create(AuthUserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesGymBranchApi(retrofit: Retrofit) : GymBranchApi {
        return retrofit.create(GymBranchApi::class.java)
    }
}