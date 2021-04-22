package com.dicoding.picodiploma.movies.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.vo.Resource

class MovieViewModel(private val repository: Repository): ViewModel() {

    fun getMovie(): LiveData<Resource<PagedList<DataMovie>>> = repository.getAllMovie()
}