package com.example.maverickshows.app.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val adult: Boolean = true,
    @SerialName("backdrop_path")
    val backgroundImg: String?,
    @SerialName("belongs_to_collection")
    val collection: String?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerialName("imdb_id")
    val imdbId: String,
    @SerialName("origin_country")
    val country: String?,
    @SerialName("original_language")
    val language: String?,
    @SerialName("original_title")
    val title2: String?,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val img: String?,
    @SerialName("production_companies")
    val companies: List<Companies>,
    @SerialName("production_countries")
    val countries: List<ProductionCountries>,
    @SerialName("release_date")
    val date: String,
    val revenue: Int,
    val runtime: Int,
    @SerialName("spoken_languages")
    val languages: List<Language>,
    val status: String,
    val tagline: String?,
    val title: String?,
    val video: Boolean,
    @SerialName("vote_average")
    val avg: Double,
    @SerialName("vote_count")
    val count: Int
)

@Serializable
data class TvDetails(
    val adult: Boolean = true,
    @SerialName("backdrop_path")
    val backgroundImg: String?,
    @SerialName("created_by")
    val creators: List<Creator>,
    @SerialName("episode_run_time")
    val runtime: List<Int>,
    @SerialName("first_air_date")
    val firstDate: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerialName("in_production")
    val inProduction: Boolean,
    val languages: List<String>,
    @SerialName("last_air_date")
    val lastDate: String,
    @SerialName("last_episode_to_air")
    val lastEpisode: Episode,
    val name: String?,
    @SerialName("next_episode_to_air")
    val nextEpisode: Episode,
    val networks: List<Companies>,
    @SerialName("number_of_episodes")
    val numEpisodes: Int,
    @SerialName("number_of_seasons")
    val numSeasons: Int,
    @SerialName("origin_country")
    val country: List<String>,
    @SerialName("original_language")
    val language: String?,
    @SerialName("original_name")
    val name2: String?,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val img: String?,
    @SerialName("production_companies")
    val companies: List<Companies>,
    @SerialName("production_countries")
    val countries: List<ProductionCountries>,
    val seasons: List<Season>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<Language>,
    val status: String,
    val tagline: String?,
    val type: String,
    @SerialName("vote_average")
    val avg: Double,
    @SerialName("vote_count")
    val count: Int
)

@Serializable
data class Season(
    @SerialName("air_date")
    val date: String,
    @SerialName("episode_count")
    val episodes: Int,
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path")
    val img: String?,
    @SerialName("season_number")
    val season: Int,
    @SerialName("vote_average")
    val avg: Double
)

@Serializable
data class Episode(
    val id: Int,
    val name: String?,
    val overview: String?,
    @SerialName("vote_average")
    val avg: Double,
    @SerialName("vote_count")
    val count: Int,
    @SerialName("air_date")
    val date: String,
    @SerialName("episode_number")
    val number: Int,
    @SerialName("episode_type")
    val type: String,
    @SerialName("production_code")
    val production: String,
    val runtime: Int,
    @SerialName("season_number")
    val season: Int,
    @SerialName("show_id")
    val showId: Int,
    @SerialName("still_path")
    val stillPath: String?
)

@Serializable
data class Creator(
    val id: Int,
    @SerialName("credit_id")
    val creditId: String,
    val name: String,
    @SerialName("original_name")
    val name2: String,
    val gender: Int,
    @SerialName("profile_path")
    val profile: String?,
)

@Serializable
data class Language(
    @SerialName("english_name")
    val en: String,
    @SerialName("iso_639_1")
    val id: String,
    val name: String
)

@Serializable
data class Companies(
    val id: Int,
    @SerialName("logo_path")
    val logo: String,
    val name: String,
    @SerialName("origin_country")
    val country: String
)

@Serializable
data class ProductionCountries(
    @SerialName("iso_3166_1")
    val id: String,
    val name: String
)