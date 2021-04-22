package com.dicoding.picodiploma.movies.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie

class MovieFavoriteViewModel(private val repository: Repository): ViewModel() {

    fun getFavoriteMovies(): LiveData<PagedList<DataMovie>> = repository.getFavoriteMovie()

    fun setMovieFavorite(movie: DataMovie) {
        val state = !movie.favorite
        repository.setFavoriteMovie(movie, state)
    }
}