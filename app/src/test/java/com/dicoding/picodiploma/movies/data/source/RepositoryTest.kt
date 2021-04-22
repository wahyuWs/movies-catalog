package com.dicoding.picodiploma.movies.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.picodiploma.movies.data.source.local.LocalDataSource
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.data.source.local.room.MoviesDao
import com.dicoding.picodiploma.movies.data.source.remote.RemoteDataSource
import com.dicoding.picodiploma.movies.utils.AppExecutors
import com.dicoding.picodiploma.movies.utils.DataDummy
import com.dicoding.picodiploma.movies.utils.LiveDataTestUtil
import com.dicoding.picodiploma.movies.utils.PagedListUtil
import com.dicoding.picodiploma.movies.vo.Resource
import org.junit.Test
import org.junit.Assert.*
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.mockito.Mockito.*

class RepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val daoMethod = mock(MoviesDao::class.java)
    private val repository = FakeRepository(remote, local, appExecutors)

    private val movieResponse = DataDummy.generateRemoteMovie()
    private val movieTitle = movieResponse[0].title
    private val tvShowResponse = DataDummy.generateRemoteTvShow()
    private val tvShowTitle = tvShowResponse[0].title

    @Test
    fun getAllMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DataMovie>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        repository.getAllMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataMovie()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DataTvShow>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        repository.getAllTvShow()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataTvShow()))
        verify(local).getAllTvShows()
        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieWithTitle() {
        val dummyEntity = MutableLiveData<DataMovie>()
        dummyEntity.value = DataDummy.generateDataMovie()[0]
        `when`(local.getMovieWithTitle(movieTitle)).thenReturn(dummyEntity)

        val movieEntities = LiveDataTestUtil.getValue(repository.getMovieWithTitle(movieTitle))
        verify(local).getMovieWithTitle(movieTitle)

        assertNotNull(movieEntities.data)
        assertEquals(movieResponse[0].title, movieEntities.data?.title)
    }

    @Test
    fun getTvShowWithTitle() {
        val dummyEntity = MutableLiveData<DataTvShow>()
        dummyEntity.value = DataDummy.generateDataTvShow()[0]
        `when`(local.getTvShowWithTitle(tvShowTitle)).thenReturn(dummyEntity)

        val tvShowEntities = LiveDataTestUtil.getValue(repository.getTvShowWithTitle(tvShowTitle))
        verify(local).getTvShowWithTitle(tvShowTitle)

        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponse[0].title, tvShowEntities.data?.title)
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DataMovie>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        repository.getFavoriteMovie()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataMovie()))
        verify(local).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DataTvShow>
        `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        repository.getFavoriteTvShow()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataTvShow()))
        verify(local).getFavoriteTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun setFavoriteMovie() {
        val localData = LocalDataSource.getInstance(daoMethod)
        val dataEntity = DataDummy.generateDataMovie()[0]
        val expectedData = dataEntity.copy(favorite = true)
        doNothing().`when`(daoMethod).updateMovie(expectedData)
        localData.setFavoriteMovies(dataEntity, true)
        verify(daoMethod, times(1)).updateMovie(expectedData)
    }

    @Test
    fun setFavoriteTvShow() {
        val localData = LocalDataSource.getInstance(daoMethod)
        val dataEntity = DataDummy.generateDataTvShow()[0]
        val expectedData = dataEntity.copy(favorite = true)
        doNothing().`when`(daoMethod).updateTvShow(expectedData)
        localData.setFavoriteTvShows(dataEntity, true)
        daoMethod.updateTvShow(expectedData)
        verify(daoMethod, times(1)).updateTvShow(expectedData)
    }
}