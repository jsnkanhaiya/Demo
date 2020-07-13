package com.example.demo.network

import android.content.Context
import androidx.annotation.NonNull
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    var context: Context? = null

    operator fun invoke(context: Context): RetrofitClient {
        RetrofitClient.context = context
        return this
    }

    private val BASE_URL = "https://api.nasa.gov/planetary/apod/"
    private val READ_TIMEOUT_SECONDS = 60
    private val WRITE_TIMEOUT_SECONDS = 60
    private val CONNECT_TIMEOUT_SECONDS = 10

    private fun getRetrofit(): Retrofit {

        val httpClient =
            getOkHttp(getHttpLogger())

        //add retro builder
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build()).build()
    }

    @NonNull
    private fun getOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): okhttp3.OkHttpClient.Builder {
        return okhttp3.OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)



    }

    @NonNull
    private fun getHttpLogger(): HttpLoggingInterceptor {
       /* return if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        } else {*/
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
           // }
        }
        return httpLoggingInterceptor
    }

    fun getClient(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }


}