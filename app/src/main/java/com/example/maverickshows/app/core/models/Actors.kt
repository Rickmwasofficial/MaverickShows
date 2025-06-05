package com.example.maverickshows.app.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val adult: Boolean = true,
    @SerialName("also_known_as")
    val aka: List<String>,
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    @SerialName("imdb_id")
    val imdb: String?,
    @SerialName("known_for_department")
    val department: String?,
    val name: String,
    @SerialName("place_of_birth")
    val birthPlace: String?,
    val popularity: Double,
    @SerialName("profile_path")
    val profile: String?
)

@Serializable
data class ActorImages(
    val id: Int,
    val profiles: List<Backdrop>
)

@Serializable
data class MovieFilmography(
    val cast: List<MovieCast>,
    val crew: List<MovieCrew>,
    val id: Int
)

@Serializable
data class MovieCast(
    @SerialName("backdrop_path")
    val backgroundImg: String?, // Made nullable
    val id: Int,
    val title: String?, // Made nullable (for movies)
    @SerialName("original_title")
    val title2: String?, // Made nullable
    val overview: String?, // Made nullable
    @SerialName("poster_path")
    val img: String?, // Made nullable
    val adult: Boolean,
    @SerialName("original_language")
    val language: String?, // Made nullable
    @SerialName("genre_ids")
    val genres: List<Int>?, // Made nullable
    val popularity: Double,
    @SerialName("release_date")
    val released: String?, // Made nullable
    val video: Boolean,
    @SerialName("first_credit_air_date")
    val firstCreditAirDate: String? = "Null",
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int,
    val character: String?,
    val order: Int?,
    @SerialName("credit_id")
    val credit: String?,
)

@Serializable
data class MovieCrew(
    @SerialName("backdrop_path")
    val backgroundImg: String?, // Made nullable
    val id: Int,
    val title: String?, // Made nullable (for movies)
    @SerialName("original_title")
    val title2: String?, // Made nullable
    val overview: String?, // Made nullable
    @SerialName("poster_path")
    val img: String?, // Made nullable
    val adult: Boolean,
    @SerialName("original_language")
    val language: String?, // Made nullable
    @SerialName("genre_ids")
    val genres: List<Int>?, // Made nullable
    val popularity: Double,
    @SerialName("release_date")
    val released: String?, // Made nullable
    val video: Boolean,
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("first_credit_air_date")
    val firstCreditAirDate: String? = "Null",
    @SerialName("vote_count")
    val votes: Int,
    @SerialName("credit_id")
    val creditId: String?,
    val department: String?,
    val job: String?
)

@Serializable
data class TvFilmography(
    val cast: List<TvCast>,
    val crew: List<TvCrew>,
    val id: Int
)

@Serializable
data class TvCast(
    @SerialName("backdrop_path")
    val backgroundImg: String?, // Made nullable
    val id: Int,
    val name: String?, // Made nullable
    @SerialName("original_name")
    val name2: String?, // Made nullable
    val overview: String?, // Made nullable
    @SerialName("poster_path")
    val img: String?, // Made nullable
    val adult: Boolean,
    @SerialName("original_language")
    val language: String?, // Made nullable
    @SerialName("genre_ids")
    val genres: List<Int>?, // Made nullable
    val popularity: Double,
    @SerialName("first_air_date")
    val firstAirDate: String?,
    @SerialName("first_credit_air_date")
    val firstCreditAirDate: String? = "Null",
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int,
    @SerialName("origin_country")
    val country: List<String>?, // Made nullable
    val character: String?,
    @SerialName("credit_id")
    val credit: String?,
    @SerialName("episode_count")
    val episodes: Int? = 0
)

@Serializable
data class TvCrew(
    @SerialName("backdrop_path")
    val backgroundImg: String?, // Made nullable
    val id: Int,
    @SerialName("first_credit_air_date")
    val firstCreditAirDate: String? = "Null",
    val name: String?, // Made nullable
    @SerialName("original_name")
    val name2: String?, // Made nullable
    val overview: String?, // Made nullable
    @SerialName("poster_path")
    val img: String?, // Made nullable
    val adult: Boolean,
    @SerialName("original_language")
    val language: String?, // Made nullable
    @SerialName("genre_ids")
    val genres: List<Int>?, // Made nullable
    val popularity: Double,
    @SerialName("first_air_date")
    val firstAirDate: String?, // Made nullable
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int,
    @SerialName("origin_country")
    val country: List<String>?,
    @SerialName("credit_id")
    val credit: String?,
    @SerialName("episode_count")
    val episodes: Int? = 0,
    val department: String?,
    val job: String?
)