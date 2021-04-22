package com.dicoding.picodiploma.movies.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GlOBAL"
    val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        espressoTestIdlingResource.decrement()
    }
}