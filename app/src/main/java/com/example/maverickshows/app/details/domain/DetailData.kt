package com.example.maverickshows.app.details.domain

import com.example.maverickshows.app.core.models.Cast
import com.example.maverickshows.app.core.models.Companies
import com.example.maverickshows.app.core.models.Crew
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.models.MovieDetails
import com.example.maverickshows.app.core.models.TvDetails
import kotlinx.serialization.Serializable

@Serializable
data class DetailData(
    val id: Int,
    val popularity: Double?,
    val avg: Double?,
    val bg: String?,
    val title: String?,
    val overview: String?,
    val count: Int?,
    val genres: List<Genre>,
    val companies: List<Companies>,
    val date: String?,
    val status: String?,
    val language: String?,
    val runtime: String?
)

fun MovieDetails.toDetailData() = DetailData(
    id = id,
    popularity = popularity,
    avg = avg,
    bg = img,
    title = title,
    overview = overview,
    count = count,
    genres = genres,
    companies = companies,
    date = date,
    status = status,
    runtime = runtime.toString(),
    language = language
)

fun TvDetails.toDetailData() = DetailData(
    id = id,
    popularity = popularity,
    avg = avg,
    bg = img,
    title = name,
    overview = overview,
    count = count,
    genres = genres,
    companies = companies,
    date = firstDate,
    status = status,
    runtime = runtime?.getOrNull(0)?.toString(),
    language = language
)

@Serializable
data class DetailCredits(
    val name: String,
    val role: String,
    val department: String,
    val id: Int,
    val profile: String?
)

fun Cast.toDetailCredits() = DetailCredits(
    name = name,
    role = character,
    department = department.toString(),
    id = id,
    profile = profile,
)

fun Crew.toDetailCredits() = DetailCredits(
    name = name,
    role = job.toString(),
    department = department.toString(),
    id = id,
    profile = profile
)