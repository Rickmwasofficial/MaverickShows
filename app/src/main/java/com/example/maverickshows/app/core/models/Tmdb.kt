package com.example.maverickshows.app.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllTrending(
    val page: Int,
    val results: List<Results>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class Genres(
    val genres: List<Genre>
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)

@Serializable
data class TvData(
    val page: Int,
    val results: List<Tv>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class TrendingTv(
    val page: Int,
    val results: List<TvResults>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class NowPlayingAndUpcomingMovies(
    val dates: Dates,
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class PopularAndTopRatedMovies(
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String
)

@Serializable
data class TvResults(
    @SerialName("backdrop_path")
    val backgroundImg: String,
    val id: Int,
    val name: String,
    @SerialName("original_name")
    val name2: String,
    val overview: String,
    @SerialName("poster_path")
    val img: String,
    @SerialName("media_type")
    val type: String,
    val adult: Boolean,
    @SerialName("original_language")
    val language: String,
    @SerialName("genre_ids")
    val genres: List<Int>,
    val popularity: Double,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int,
    @SerialName("origin_country")
    val country: List<String>
)

@Serializable
data class Tv(
    @SerialName("backdrop_path")
    val backgroundImg: String,
    val id: Int,
    val name: String,
    @SerialName("original_name")
    val name2: String,
    val overview: String,
    @SerialName("poster_path")
    val img: String,
    val adult: Boolean,
    @SerialName("original_language")
    val language: String,
    @SerialName("genre_ids")
    val genres: List<Int>,
    val popularity: Double,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int,
    @SerialName("origin_country")
    val country: List<String>
)

@Serializable
data class Results(
    @SerialName("backdrop_path")
    val backgroundImg: String,
    val id: Int,
    val title: String,
    @SerialName("original_title")
    val title2: String,
    val overview: String,
    @SerialName("poster_path")
    val img: String,
    @SerialName("media_type")
    val type: String,
    val adult: Boolean,
    @SerialName("original_language")
    val language: String,
    @SerialName("genre_ids")
    val genres: List<Int>,
    val popularity: Double,
    @SerialName("release_date")
    val released: String,
    val video: Boolean,
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int
)

@Serializable
data class Movie(
    @SerialName("backdrop_path")
    val backgroundImg: String,
    val id: Int,
    val title: String,
    @SerialName("original_title")
    val title2: String,
    val overview: String,
    @SerialName("poster_path")
    val img: String,
    val adult: Boolean,
    @SerialName("original_language")
    val language: String,
    @SerialName("genre_ids")
    val genres: List<Int>,
    val popularity: Double,
    @SerialName("release_date")
    val released: String,
    val video: Boolean,
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int
)
