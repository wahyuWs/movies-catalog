package com.dicoding.picodiploma.movies.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM datamovies")
    fun getMovies(): DataSource.Factory<Int, DataMovie>

    @Query("SELECT * FROM datamovies WHERE title = :title")
    fun getMovieByTitle(title: String): LiveData<DataMovie>

    @Query("SELECT * FROM datamovies WHERE favorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, DataMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<DataMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMovie(movie: DataMovie)

    @Update
    fun updateMovie(movie: DataMovie)

    @Query("SELECT * FROM datatvshows")
    fun getTvShows(): DataSource.Factory<Int, DataTvShow>

    @Query("SELECT * FROM datatvshows WHERE title = :title")
    fun getTvShowByTitle(title: String): LiveData<DataTvShow>

    @Query("SELECT * FROM datatvshows WHERE favorite = 1")
    fun getFavoriteTvShow(): DataSource.Factory<Int, DataTvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<DataTvShow>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailTvShow(tvShow: DataTvShow)

    @Update
    fun updateTvShow(tvShow: DataTvShow)
}