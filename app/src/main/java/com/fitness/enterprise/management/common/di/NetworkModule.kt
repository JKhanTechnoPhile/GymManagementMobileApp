package com.fitness.enterprise.management.common.di

import com.fitness.enterprise.management.auth.api.AuthInterceptor
import com.fitness.enterprise.management.auth.api.AuthUserApi
import com.fitness.enterprise.management.branch.api.GymBranchApi
import com.fitness.enterprise.management.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Builder {
        return Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOKHTTPClient(authInterceptor: AuthInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesAuthUserApi(retrofitBuilder: Builder) : AuthUserApi {
        return retrofitBuilder.build().create(AuthUserApi::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideAuthRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .baseUrl(Constants.BASE_URL)
//            .build()
//    }

//    @Singleton
//    @Provides
//    fun providesGymBranchApi(retrofitBuilder: Builder, client: OkHttpClient) : GymBranchApi {
//        return retrofitBuilder.client(client).build().create(GymBranchApi::class.java)
//    }
}