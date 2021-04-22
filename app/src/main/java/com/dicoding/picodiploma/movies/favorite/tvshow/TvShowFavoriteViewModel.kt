package com.dicoding.picodiploma.movies.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow

class TvShowFavoriteViewModel(private val repository: Repository): ViewModel() {

    fun getFavoriteTvShows(): LiveData<PagedList<DataTvShow>> = repository.getFavoriteTvShow()

    fun setFavoriteTvShow(tvShow: DataTvShow) {
        val state = !tvShow.favorite
        repository.setFavoriteTvShow(tvShow, state)
    }
}