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
    val img2: String,
    val releaseDate: String,
    val genre: List<Int>,
    val type: String
)

fun TvResults.toHomeData() = HomeData(
    id = id,
    name = name,
    title = null,
    img = img,
    img2 = backgroundImg,
    releaseDate = firstAirDate.take(4),
    genre = genres,
    type = type
)

fun Results.toHomeData() = HomeData(
    id = id,
    name = null,
    title = title,
    img = img,
    img2 = backgroundImg,
    releaseDate = released.take(4),
    genre = genres,
    type = type
)

fun Tv.toHomeData() = HomeData(
    id = id,
    name = name,
    title = null,
    img = img,
    img2 = backgroundImg,
    releaseDate = firstAirDate.take(4),
    genre = genres,
    type = "tv"
)

fun Movie.toHomeData() = HomeData(
    id = id,
    name = null,
    title = title,
    img = img,
    img2 = backgroundImg,
    releaseDate = released.take(4),
    genre = genres,
    type = "movie"
)