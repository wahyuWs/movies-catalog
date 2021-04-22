package com.dicoding.picodiploma.movies.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.favorite.tvshow.TvShowFavoriteViewModel
import com.dicoding.picodiploma.movies.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowFavoriteViewModelTest {

    private lateinit var viewModel: TvShowFavoriteViewModel
    private val dataDummy = DataDummy.generateDataTvShow()[0]

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<PagedList<DataTvShow>>

    @Mock
    private lateinit var pagedList: PagedList<DataTvShow>

    @Before
    fun setUp() {
        viewModel = TvShowFavoriteViewModel(repository)
    }

    @Test
    fun getFavoriteTvShows() {
        val dummyTvShow = pagedList
        `when`(dummyTvShow.size).thenReturn(15)
        val tvShows = MutableLiveData<PagedList<DataTvShow>>()
        tvShows.value = dummyTvShow

        `when`(repository.getFavoriteTvShow()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getFavoriteTvShows().value
        verify(repository).getFavoriteTvShow()
        assertNotNull(tvShowEntities)
        assertEquals(15, tvShowEntities?.size)

        viewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }

    @Test
    fun setFavoriteTvShow() {
        val state = !dataDummy.favorite
        doNothing().`when`(repository).setFavoriteTvShow(dataDummy, state)
        viewModel.setFavoriteTvShow(dataDummy)
        verify(repository).setFavoriteTvShow(dataDummy, state)
    }
}