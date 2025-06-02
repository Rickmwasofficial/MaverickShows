package com.example.maverickshows.app.home.domain

import com.example.maverickshows.app.core.models.Movie
import com.example.maverickshows.app.core.models.Results
import com.example.maverickshows.app.core.models.Tv
import com.example.maverickshows.app.core.models.TvResults
import kotlinx.serialization.Serializable

@Serializable
data class HomeData(
    val id: Int,
    val name: String? = null,
    val title: String? = null,
    val img: String,
    val releaseDate: String,
    val genre: Int,
    val type: String
)

fun TvResults.toHomeData() = HomeData(
    id = id,
    name = name,
    title = null,
    img = img,
    releaseDate = firstAirDate.take(4),
    genre = genres[0],
    type = type
)

fun Results.toHomeData() = HomeData(
    id = id,
    name = null,
    title = title,
    img = img,
    releaseDate = released.take(4),
    genre = genres[0],
    type = type
)

fun Tv.toHomeData() = HomeData(
    id = id,
    name = name,
    title = null,
    img = img,
    releaseDate = firstAirDate.take(4),
    genre = genres[0],
    type = "tv"
)

fun Movie.toHomeData() = HomeData(
    id = id,
    name = null,
    title = title,
    img = img,
    releaseDate = released.take(4),
    genre = genres[0],
    type = "movie"
)