package com.dicoding.picodiploma.movies.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.picodiploma.movies.data.source.Repository
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.favorite.movie.MovieFavoriteViewModel
import com.dicoding.picodiploma.movies.utils.DataDummy
import com.nhaarman.mockitokotlin2.doNothing
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
class MovieFavoriteViewModelTest {

    private lateinit var viewModel: MovieFavoriteViewModel
    private val dataDummy = DataDummy.generateDataMovie()[0]

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<PagedList<DataMovie>>

    @Mock
    private lateinit var pagedList: PagedList<DataMovie>

    @Before
    fun setUp() {
        viewModel = MovieFavoriteViewModel(repository)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovie = pagedList
        `when`(dummyMovie.size).thenReturn(15)
        val movies = MutableLiveData<PagedList<DataMovie>>()
        movies.value = dummyMovie

        `when`(repository.getFavoriteMovie()).thenReturn(movies)
        val movieEntities = viewModel.getFavoriteMovies().value
        verify(repository).getFavoriteMovie()
        assertNotNull(movieEntities)
        assertEquals(15, movieEntities?.size)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun setMovieFavorite() {
        val state = !dataDummy.favorite
        doNothing().`when`(repository).setFavoriteMovie(dataDummy, state)
        viewModel.setMovieFavorite(dataDummy)
        verify(repository).setFavoriteMovie(dataDummy, state)
    }
}