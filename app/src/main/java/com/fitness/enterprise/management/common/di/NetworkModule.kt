package com.fitness.enterprise.management.common.di

import com.fitness.enterprise.management.auth.api.AuthInterceptor
import com.fitness.enterprise.management.auth.api.AuthUserApi
import com.fitness.enterprise.management.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
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
        return OkHttpClient.Builder().writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    @AuthOkHttpClientQualifier
    fun provideOKHTTPClientForAuth() : OkHttpClient {
        return OkHttpClient.Builder().writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun providesAuthUserApi(retrofitBuilder: Builder, @AuthOkHttpClientQualifier client: OkHttpClient) : AuthUserApi {
        return retrofitBuilder.client(client).build().create(AuthUserApi::class.java)
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