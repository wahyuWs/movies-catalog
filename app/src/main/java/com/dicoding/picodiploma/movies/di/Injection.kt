package com.dicoding.picodiploma.movies.di

import android.content.Context
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.LocalDataSource
import com.dicoding.picodiploma.movies.data.source.local.room.MoviesDatabase
import com.dicoding.picodiploma.movies.data.source.remote.RemoteDataSource
import com.dicoding.picodiploma.movies.utils.AppExecutors
import com.dicoding.picodiploma.movies.utils.DataDummy

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = MoviesDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(DataDummy)
        val localDataSource = LocalDataSource.getInstance(database.moviesDao())
        val appExecutors = AppExecutors()

        return Repository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}