package com.dicoding.picodiploma.movies.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.utils.DataDummy
import com.dicoding.picodiploma.movies.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailActivityViewModelTest {

    private lateinit var viewModel: DetailActivityViewModel
    private val dummyMovie = DataDummy.generateDataMovie()[0]
    private val dummyTvShow = DataDummy.generateDataTvShow()[0]
    private val titleMovie = dummyMovie.title
    private val titleTvShow = dummyTvShow.title

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var movieObserver: Observer<Resource<DataMovie>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<DataTvShow>>

    @Before
    fun setUp() {
        viewModel = DetailActivityViewModel(repository)
    }

    @Test
    fun getDetailMovie() {
        viewModel.setMovie(titleMovie)

        val dataDummy = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<DataMovie>>()
        movie.value = dataDummy

        `when`(repository.getMovieWithTitle(titleMovie)).thenReturn(movie)

        viewModel.detailMovie.observeForever(movieObserver)
        verify(movieObserver).onChanged(dataDummy)
    }

    @Test
    fun getDetailTvShow() {
        viewModel.setTvShow(titleTvShow)

        val dataDummy = Resource.success(dummyTvShow)
        val tvShow = MutableLiveData<Resource<DataTvShow>>()
        tvShow.value = dataDummy

        `when`(repository.getTvShowWithTitle(titleTvShow)).thenReturn(tvShow)

        viewModel.detailTvShow.observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dataDummy)
    }

    @Test
    fun setMovieFavorite() {
        val dataResource = viewModel.detailMovie.value
        val dataMovie = dataResource?.data
        if (dataMovie != null) {
            doNothing().`when`(repository).setFavoriteMovie(dataMovie, true)
            viewModel.setMovieFavorite()
            verify(repository, times(1)).setFavoriteMovie(dummyMovie, true)
        }
    }

    @Test
    fun setTvShowFavorite() {
        val dataResource = viewModel.detailTvShow.value
        val dataTvShow = dataResource?.data
        if (dataTvShow != null) {
            doNothing().`when`(repository).setFavoriteTvShow(dataTvShow, true)
            viewModel.setTvShowFavorite()
            verify(repository, times(1)).setFavoriteTvShow(dummyTvShow, true)
        }
    }
}