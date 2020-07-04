package com.example.demo.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(context: Application): Context = context

    @Provides
    @Singleton
    fun provideAssetManager(context: Context) =  context.assets

    @Provides
    @Singleton
    fun provideGson() = Gson()

}