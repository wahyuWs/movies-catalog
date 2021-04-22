package com.dicoding.picodiploma.movies.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow

@Database(entities = [DataMovie::class, DataTvShow::class], version = 1, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    MoviesDatabase::class.java, "Movies.db").build()
            }
    }
}