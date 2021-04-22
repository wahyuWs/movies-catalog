package com.dicoding.picodiploma.movies.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.data.source.local.room.MoviesDao

class LocalDataSource private constructor(private val mMoviesDao: MoviesDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(moviesDao: MoviesDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(moviesDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getAllMovies(): DataSource.Factory<Int, DataMovie> = mMoviesDao.getMovies()

    fun getFavoriteMovies(): DataSource.Factory<Int, DataMovie> = mMoviesDao.getFavoriteMovie()

    fun getAllTvShows(): DataSource.Factory<Int, DataTvShow> = mMoviesDao.getTvShows()

    fun getFavoriteTvShows(): DataSource.Factory<Int, DataTvShow> = mMoviesDao.getFavoriteTvShow()

    fun getMovieWithTitle(title: String): LiveData<DataMovie> = mMoviesDao.getMovieByTitle(title)

    fun getTvShowWithTitle(title: String): LiveData<DataTvShow> = mMoviesDao.getTvShowByTitle(title)

    fun insertMovies(movie: List<DataMovie>) = mMoviesDao.insertMovie(movie)

    fun insertDetailMovie(movie: DataMovie) = mMoviesDao.insertDetailMovie(movie)

    fun setFavoriteMovies(movie: DataMovie, state: Boolean) {
        movie.favorite = state
        mMoviesDao.updateMovie(movie)
    }

    fun insertTvShows(tvShow: List<DataTvShow>) = mMoviesDao.insertTvShow(tvShow)

    fun insertDetailTvShow(tvShow: DataTvShow) = mMoviesDao.insertDetailTvShow(tvShow)

    fun setFavoriteTvShows(tvShow: DataTvShow, state: Boolean) {
        tvShow.favorite = state
        mMoviesDao.updateTvShow(tvShow)
    }
}