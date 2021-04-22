package com.dicoding.picodiploma.movies.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
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
class MovieViewModelTest {

    private lateinit var viewmodel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<DataMovie>>>

    @Mock
    private lateinit var pagedList: PagedList<DataMovie>

    @Before
    fun setUp() {
        viewmodel = MovieViewModel(repository)
    }

    @Test
    fun getMovie() {
        val dummyMovie = Resource.success(pagedList)
        `when`(dummyMovie.data?.size).thenReturn(15)
        val movie = MutableLiveData<Resource<PagedList<DataMovie>>>()
        movie.value = dummyMovie

        `when`(repository.getAllMovie()).thenReturn(movie)
        val movieList = viewmodel.getMovie().value?.data
        verify(repository).getAllMovie()
        assertNotNull(movieList)
        assertEquals(15, movieList?.size)

        viewmodel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}