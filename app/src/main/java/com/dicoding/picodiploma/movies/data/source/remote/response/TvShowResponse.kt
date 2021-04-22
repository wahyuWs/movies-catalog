package com.dicoding.picodiploma.movies.data.source.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowResponse(
    var image: Int,
    var title: String,
    var description: String,
    var releaseDate: String,
    var duration: String,
    var status: String,
    var genre: String,
    var rate: String,
): Parcelable
