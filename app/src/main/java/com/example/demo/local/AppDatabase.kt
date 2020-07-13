package com.example.demo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demo.local.Entities.ApodEntity
import com.example.demo.local.dao.ApodDao

@Database(entities = [ApodEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract val apodDao : ApodDao
}