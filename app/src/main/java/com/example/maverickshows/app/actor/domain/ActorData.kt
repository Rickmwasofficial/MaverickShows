package com.example.maverickshows.app.actor.domain

import com.example.maverickshows.app.core.models.Actor
import com.example.maverickshows.app.core.models.MovieCast
import com.example.maverickshows.app.core.models.MovieCrew
import com.example.maverickshows.app.core.models.TvCast
import com.example.maverickshows.app.core.models.TvCrew

data class ActorData(
    val name: String,
    val biography: String,
    val gender: String,
    val id: String,
    val department: String,
    val birthPlace: String,
    val profile: String,
    val birthday: String
)

data class FilmographyData(
    val id: Int,
    val title: String? = null,
    val img: String,
    val img2: String,
    val releaseDate: String,
    val genre: List<Int>,
    val type: String
)

fun MovieCast.toFilmography() = FilmographyData(
    id = id,
    title = title,
    img = img.toString(),
    img2 = backgroundImg.toString(),
    releaseDate = released.toString(),
    genre = genres ?: listOf(),
    type = "movie"
)

fun MovieCrew.toFilmography() = FilmographyData(
    id = id,
    title = title,
    img = img.toString(),
    img2 = backgroundImg.toString(),
    releaseDate = released.toString(),
    genre = genres ?: listOf(),
    type = "movie"
)

fun TvCast.toFilmography() = FilmographyData(
    id = id,
    title = name,
    img = img.toString(),
    img2 = backgroundImg.toString(),
    releaseDate = firstAirDate.toString(),
    genre = genres ?: listOf(),
    type = "tv"
)

fun TvCrew.toFilmography() = FilmographyData(
    id = id,
    title = name,
    img = img.toString(),
    img2 = backgroundImg.toString(),
    releaseDate = firstAirDate.toString(),
    genre = genres ?: listOf(),
    type = "tv"
)

fun Actor.toActorData() = ActorData(
    name = name,
    biography = biography.toString(),
    gender = when (gender) {
        0 -> "Not Set"
        1 -> "Female"
        2 -> "Male"
        else -> "Non-Binary"
    },
    id = id.toString(),
    department = department.toString(),
    birthPlace = birthPlace.toString(),
    profile = profile.toString(),
    birthday = birthday.toString()
)