package com.dicoding.picodiploma.movies.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<DataTvShow>>>

    @Mock
    private lateinit var pagedList: PagedList<DataTvShow>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repository)
    }

    @Test
    fun getTvShow() {
        val dummyTvShow = Resource.success(pagedList)
        `when`(dummyTvShow.data?.size).thenReturn(15)
        val tvShow = MutableLiveData<Resource<PagedList<DataTvShow>>>()
        tvShow.value = dummyTvShow

        `when`(repository.getAllTvShow()).thenReturn(tvShow)
        val tvShowList = viewModel.getTvShow().value?.data
        verify(repository).getAllTvShow()
        assertNotNull(tvShowList)
        assertEquals(15, tvShowList?.size)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }
}