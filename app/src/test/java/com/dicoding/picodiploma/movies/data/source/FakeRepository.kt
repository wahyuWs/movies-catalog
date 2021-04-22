package com.dicoding.picodiploma.movies.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.NetworkBoundResource
import com.dicoding.picodiploma.movies.data.source.local.LocalDataSource
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.data.source.remote.ApiResponse
import com.dicoding.picodiploma.movies.data.source.remote.RemoteDataSource
import com.dicoding.picodiploma.movies.data.source.remote.response.MovieResponse
import com.dicoding.picodiploma.movies.data.source.remote.response.TvShowResponse
import com.dicoding.picodiploma.movies.utils.AppExecutors
import com.dicoding.picodiploma.movies.vo.Resource

class FakeRepository constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors): MoviesDataSource {

    override fun getAllMovie(): LiveData<Resource<PagedList<DataMovie>>> {
        return object : NetworkBoundResource<PagedList<DataMovie>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<DataMovie>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<DataMovie>?): Boolean =
                    data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                    remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<DataMovie>()
                for (response in data) {
                    val movie = DataMovie(response.image,
                            response.title,
                            response.description,
                            response.releaseDate,
                            response.duration,
                            response.status,
                            response.genre,
                            response.rate)

                    movieList.add(movie)
                }

                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovie(): LiveData<PagedList<DataMovie>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getAllTvShow(): LiveData<Resource<PagedList<DataTvShow>>> {
        return object : NetworkBoundResource<PagedList<DataTvShow>, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<DataTvShow>> {
                val config = PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPageSize(4)
                        .build()

                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<DataTvShow>?): Boolean =
                    data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                    remoteDataSource.getAllTvShows()

            override fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = ArrayList<DataTvShow>()
                for (response in data) {
                    val tvShow = DataTvShow(response.image,
                            response.title,
                            response.description,
                            response.releaseDate,
                            response.duration,
                            response.status,
                            response.genre,
                            response.rate)

                    tvShowList.add(tvShow)
                }

                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<DataTvShow>> {
        val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(4)
                .setPageSize(4)
                .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun getMovieWithTitle(title: String): LiveData<Resource<DataMovie>> {
        return object : NetworkBoundResource<DataMovie, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<DataMovie> =
                    localDataSource.getMovieWithTitle(title)

            override fun shouldFetch(data: DataMovie?): Boolean =
                    data == null

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                    remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                lateinit var movieDetail: DataMovie
                for (response in data) {
                    if (response.title == title) {
                        movieDetail = DataMovie(response.image,
                                response.title,
                                response.description,
                                response.releaseDate,
                                response.duration,
                                response.status,
                                response.genre,
                                response.rate)
                    }
                }

                localDataSource.insertDetailMovie(movieDetail)
            }
        }.asLiveData()
    }

    override fun getTvShowWithTitle(title: String): LiveData<Resource<DataTvShow>> {
        return object : NetworkBoundResource<DataTvShow, List<TvShowResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<DataTvShow> =
                    localDataSource.getTvShowWithTitle(title)

            override fun shouldFetch(data: DataTvShow?): Boolean =
                    data == null

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                    remoteDataSource.getAllTvShows()

            override fun saveCallResult(data: List<TvShowResponse>) {
                lateinit var tvShow: DataTvShow
                for (response in data) {
                    if (response.title == title) {
                        tvShow = DataTvShow(response.image,
                                response.title,
                                response.description,
                                response.releaseDate,
                                response.duration,
                                response.status,
                                response.genre,
                                response.rate)
                    }
                }

                localDataSource.insertDetailTvShow(tvShow)
            }
        }.asLiveData()
    }

    override fun setFavoriteMovie(movie: DataMovie, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovies(movie, state) }
    }

    override fun setFavoriteTvShow(tvShow: DataTvShow, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShows(tvShow, state) }
    }
}