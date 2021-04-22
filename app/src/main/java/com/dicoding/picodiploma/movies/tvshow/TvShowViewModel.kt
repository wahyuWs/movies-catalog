package com.dicoding.picodiploma.movies.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.vo.Resource

class TvShowViewModel(private val repository: Repository): ViewModel() {

    fun getTvShow(): LiveData<Resource<PagedList<DataTvShow>>> = repository.getAllTvShow()
}