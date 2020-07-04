package com.example.demo.di.component

import android.app.Application
import com.example.demo.base.BaseApplication
import com.example.demo.di.ActivityBuilderModule
import com.example.demo.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,ActivityBuilderModule::class,AppModule::class]
)
interface ApplicationComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): ApplicationComponent
    }
}