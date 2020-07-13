package com.example.demo.di.module

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.demo.base.BaseApplication
import com.example.demo.local.AppDatabase
import com.example.demo.local.Entities.ApodEntity
import com.example.demo.local.dao.ApodDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideApodDatabase(application: Application) =
        Room.databaseBuilder(application, AppDatabase::class.java, "APOD-db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()


    @Singleton
    @Provides
    fun provideApodDao(apodDatabase: AppDatabase): ApodDao {
        return apodDatabase.apodDao
    }





}
