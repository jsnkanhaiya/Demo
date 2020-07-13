package com.example.demo.base

import android.util.Log
import com.example.demo.di.component.DaggerApplicationComponent
import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        RxJavaPlugins.setErrorHandler {
            Log.d("Error",it.message)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }
}