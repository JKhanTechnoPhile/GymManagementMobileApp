package com.fitness.enterprise.management.subscription.di

import com.fitness.enterprise.management.subscription.api.GymSubscriptionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class GymSubscriptionModule {

    @Singleton
    @Provides
    fun providesGymSubscriptionApi(retrofitBuilder: Retrofit.Builder, client: OkHttpClient) : GymSubscriptionApi {
        return retrofitBuilder.client(client).build().create(GymSubscriptionApi::class.java)
    }
}