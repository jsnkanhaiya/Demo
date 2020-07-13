package com.example.demo.di.module

import com.example.demo.BuildConfig
import com.example.demo.network.ApiService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val BASE_URL = "https://ldcricket.sportz.io"
    private val READ_TIMEOUT_SECONDS = 60
    private val WRITE_TIMEOUT_SECONDS = 60
    private val CONNECT_TIMEOUT_SECONDS = 10

    @Provides
    @Singleton
    fun provideOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build()).build()
    }


    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }
        }
    }


    @Provides
    @Singleton
    fun provideItemService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
