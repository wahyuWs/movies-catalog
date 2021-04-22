package com.dicoding.picodiploma.movies.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "datatvshows")
data class DataTvShow(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "image")
    var image: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "releasedate")
    var releaseDate: String,

    @ColumnInfo(name = "duration")
    var duration: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "genre")
    var genre: String,

    @ColumnInfo(name = "rate")
    var rate: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)
