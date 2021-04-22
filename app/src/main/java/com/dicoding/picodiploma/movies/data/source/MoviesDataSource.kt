package com.dicoding.picodiploma.movies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.vo.Resource

interface MoviesDataSource {

    fun getAllMovie(): LiveData<Resource<PagedList<DataMovie>>>

    fun getFavoriteMovie(): LiveData<PagedList<DataMovie>>

    fun getAllTvShow(): LiveData<Resource<PagedList<DataTvShow>>>

    fun getFavoriteTvShow(): LiveData<PagedList<DataTvShow>>

    fun getMovieWithTitle(title: String): LiveData<Resource<DataMovie>>

    fun getTvShowWithTitle(title: String): LiveData<Resource<DataTvShow>>

    fun setFavoriteMovie(movie: DataMovie, state: Boolean)

    fun setFavoriteTvShow(tvShow: DataTvShow, state: Boolean)
}