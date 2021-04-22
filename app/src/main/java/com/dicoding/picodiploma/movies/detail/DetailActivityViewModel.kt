package com.dicoding.picodiploma.movies.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.vo.Resource

class DetailActivityViewModel(private val repository: Repository): ViewModel() {
    val title = MutableLiveData<String>()

    fun setMovie(title: String) {
        this.title.value = title
    }

    fun setTvShow(title: String) {
        this.title.value = title
    }

    var detailMovie: LiveData<Resource<DataMovie>> = Transformations.switchMap(title) { mTitleMovie ->
        repository.getMovieWithTitle(mTitleMovie)
    }

    var detailTvShow: LiveData<Resource<DataTvShow>> = Transformations.switchMap(title) { mTitleTvShow ->
        repository.getTvShowWithTitle(mTitleTvShow)
    }

    fun setMovieFavorite() {
        val movieResource = detailMovie.value
        if (movieResource != null) {
            val movie = movieResource.data
            if (movie != null) {
                val state = !movie.favorite
                repository.setFavoriteMovie(movie, state)
            }
        }
    }

    fun setTvShowFavorite() {
        val tvShowResource = detailTvShow.value
        if (tvShowResource != null) {
            val tvShow = tvShowResource.data
            if (tvShow != null) {
                val state = !tvShow.favorite
                repository.setFavoriteTvShow(tvShow, state)
            }
        }
    }
}