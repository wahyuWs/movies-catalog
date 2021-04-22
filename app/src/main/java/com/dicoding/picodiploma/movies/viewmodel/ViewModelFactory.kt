package com.dicoding.picodiploma.movies.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.detail.DetailActivityViewModel
import com.dicoding.picodiploma.movies.di.Injection
import com.dicoding.picodiploma.movies.favorite.movie.MovieFavoriteViewModel
import com.dicoding.picodiploma.movies.favorite.tvshow.TvShowFavoriteViewModel
import com.dicoding.picodiploma.movies.movie.MovieViewModel
import com.dicoding.picodiploma.movies.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mRepository: Repository): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                return TvShowViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java) -> {
                return MovieFavoriteViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(TvShowFavoriteViewModel::class.java) -> {
                return TvShowFavoriteViewModel(mRepository) as T
            }
            modelClass.isAssignableFrom(DetailActivityViewModel::class.java) -> {
                return DetailActivityViewModel(mRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }
    }
}