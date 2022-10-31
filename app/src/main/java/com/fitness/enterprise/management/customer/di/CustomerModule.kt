package com.fitness.enterprise.management.customer.di

import com.fitness.enterprise.management.customer.api.CustomerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CustomerModule {

    @Singleton
    @Provides
    fun providesGymSubscriptionApi(retrofitBuilder: Retrofit.Builder, client: OkHttpClient) : CustomerApi {
        return retrofitBuilder.client(client).build().create(CustomerApi::class.java)
    }
}