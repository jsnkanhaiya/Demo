package com.example.demo.local.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "TB_APOD")
data class ApodEntity (

    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "copyright")
    val copyright: String,

    @ColumnInfo(name = "media_type")
    val mediaType: String,

    @ColumnInfo(name = "hdurl")
    val hdurl: String,

    @ColumnInfo(name = "service_version")
    val serviceVersion: String,

    @ColumnInfo(name = "explanation")
    val explanation: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String


)