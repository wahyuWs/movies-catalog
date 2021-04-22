package com.dicoding.picodiploma.movies.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.movies.data.source.remote.response.MovieResponse
import com.dicoding.picodiploma.movies.data.source.remote.response.TvShowResponse
import com.dicoding.picodiploma.movies.utils.DataDummy
import com.dicoding.picodiploma.movies.utils.EspressoIdlingResource

class RemoteDataSource private constructor(private val dataDummy: DataDummy) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 3000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(data: DataDummy): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(data)
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed({
            resultMovie.value = ApiResponse.success(dataDummy.generateRemoteMovie())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }

    fun getAllTvShows(): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShowResponse>>>()
        handler.postDelayed({
            resultTvShow.value = ApiResponse.success(dataDummy.generateRemoteTvShow())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultTvShow
    }
}