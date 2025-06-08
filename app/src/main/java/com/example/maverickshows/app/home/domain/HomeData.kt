package com.example.maverickshows.app.home.domain

import com.example.maverickshows.app.core.models.Movie
import com.example.maverickshows.app.core.models.MovieDetails
import com.example.maverickshows.app.core.models.Results
import com.example.maverickshows.app.core.models.Tv
import com.example.maverickshows.app.core.models.TvDetails
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
    img = img?: "",
    img2 = backgroundImg ?: "",
    releaseDate = firstAirDate?.take(4) ?: "N/A",
    genre = genres.orEmpty(),
    type = type ?: "tv"
)

fun Results.toHomeData() = HomeData(
    id = id,
    name = null,
    title = title,
    img = img ?: "",
    img2 = backgroundImg ?: "",
    releaseDate = released?.take(4) ?: "N/A",
    genre = genres.orEmpty(),
    type = type ?: "unknown"
)

fun Tv.toHomeData() = HomeData(
    id = id,
    name = name,
    title = null,
    img = img ?: "",
    img2 = backgroundImg ?: "",
    releaseDate = firstAirDate?.take(4) ?: "N/A",
    genre = genres.orEmpty(),
    type = "tv"
)

fun Movie.toHomeData() = HomeData(
    id = id,
    name = null,
    title = title,
    img = img ?: "",
    img2 = backgroundImg ?: "",
    releaseDate = released?.take(4) ?: "N/A",
    genre = genres.orEmpty(),
    type = "movie"
)

fun TvDetails.toHomeData() = HomeData(
    id = id,
    name = name,
    title = null,
    img = img ?: "",
    img2 = backgroundImg ?: "",
    releaseDate = firstDate?.take(4) ?: "N/A",
    genre = listOf(genres[0].id),
    type = "tv"
)

fun MovieDetails.toHomeData() = HomeData(
    id = id,
    name = null,
    title = title,
    img = img ?: "",
    img2 = backgroundImg ?: "",
    releaseDate = date?.take(4) ?: "N/A",
    genre = listOf(genres[0].id),
    type = "movie"
)