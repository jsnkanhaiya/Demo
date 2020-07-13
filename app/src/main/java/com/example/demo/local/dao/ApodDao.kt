package com.example.demo.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demo.local.Entities.ApodEntity
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ApodDao {

        @Query("Select * from TB_APOD")
        fun getApodAllData(): Single<List<ApodEntity>>

        @Insert
        fun insertApodAllData(apodDataList: List<ApodEntity>)

        @Query("SELECT * FROM TB_APOD WHERE date LIKE :search")
        fun isDataAvailable(search: String?):  Single<List<ApodEntity>>

}