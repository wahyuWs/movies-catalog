package com.dicoding.picodiploma.movies

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dicoding.picodiploma.movies.utils.DataDummy
import com.dicoding.picodiploma.movies.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest {

    private val dummyMovie = DataDummy.generateDataMovie()
    private val dummyTvShow = DataDummy.generateDataTvShow()

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.title_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.title_movie)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(withText(dummyMovie[0].rate)))
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_description)).check(matches(withText(dummyMovie[0].description)))
        onView(withId(R.id.genre)).check(matches(isDisplayed()))
        onView(withId(R.id.genre)).check(matches(withText(dummyMovie[0].genre)))
        onView(withId(R.id.release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date)).check(matches(withText(dummyMovie[0].releaseDate)))
        onView(withId(R.id.duration)).check(matches(isDisplayed()))
        onView(withId(R.id.duration)).check(matches(withText(dummyMovie[0].duration)))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(withText(dummyMovie[0].status)))
        onView(withId(R.id.status_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.image_share)).check(matches(isDisplayed()))
        onView(withId(R.id.image_share)).perform(click())
    }

    @Test
    fun loadFavoriteMovie() {
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.status_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withId(R.id.rv_movie_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.title_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.title_movie)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(withText(dummyMovie[0].rate)))
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_description)).check(matches(withText(dummyMovie[0].description)))
        onView(withId(R.id.genre)).check(matches(isDisplayed()))
        onView(withId(R.id.genre)).check(matches(withText(dummyMovie[0].genre)))
        onView(withId(R.id.release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date)).check(matches(withText(dummyMovie[0].releaseDate)))
        onView(withId(R.id.duration)).check(matches(isDisplayed()))
        onView(withId(R.id.duration)).check(matches(withText(dummyMovie[0].duration)))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(withText(dummyMovie[0].status)))
        onView(withId(R.id.image_share)).check(matches(isDisplayed()))
        onView(withId(R.id.status_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.status_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun loadTvShow() {
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.title_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.title_movie)).check(matches(withText(dummyTvShow[0].title)))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(withText(dummyTvShow[0].rate)))
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_description)).check(matches(withText(dummyTvShow[0].description)))
        onView(withId(R.id.genre)).check(matches(isDisplayed()))
        onView(withId(R.id.genre)).check(matches(withText(dummyTvShow[0].genre)))
        onView(withId(R.id.release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date)).check(matches(withText(dummyTvShow[0].releaseDate)))
        onView(withId(R.id.duration)).check(matches(isDisplayed()))
        onView(withId(R.id.duration)).check(matches(withText(dummyTvShow[0].duration)))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(withText(dummyTvShow[0].status)))
        onView(withId(R.id.status_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.image_share)).check(matches(isDisplayed()))
        onView(withId(R.id.image_share)).perform(click())
    }

    @Test
    fun loadFavoriteTvShow() {
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.status_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.action_favorite)).perform(click())
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tvShow_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.title_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.title_movie)).check(matches(withText(dummyTvShow[0].title)))
        onView(withId(R.id.rating)).check(matches(isDisplayed()))
        onView(withId(R.id.rating)).check(matches(withText(dummyTvShow[0].rate)))
        onView(withId(R.id.movie_description)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_description)).check(matches(withText(dummyTvShow[0].description)))
        onView(withId(R.id.genre)).check(matches(isDisplayed()))
        onView(withId(R.id.genre)).check(matches(withText(dummyTvShow[0].genre)))
        onView(withId(R.id.release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date)).check(matches(withText(dummyTvShow[0].releaseDate)))
        onView(withId(R.id.duration)).check(matches(isDisplayed()))
        onView(withId(R.id.duration)).check(matches(withText(dummyTvShow[0].duration)))
        onView(withId(R.id.status)).check(matches(isDisplayed()))
        onView(withId(R.id.status)).check(matches(withText(dummyTvShow[0].status)))
        onView(withId(R.id.image_share)).check(matches(isDisplayed()))
        onView(withId(R.id.status_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.status_favorite)).perform(click())
        onView(isRoot()).perform(pressBack())
    }
}