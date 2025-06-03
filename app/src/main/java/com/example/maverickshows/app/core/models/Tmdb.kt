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
    val backgroundImg: String?, // Made nullable
    val id: Int,
    val name: String?, // Made nullable
    @SerialName("original_name")
    val name2: String?, // Made nullable
    val overview: String?, // Made nullable
    @SerialName("poster_path")
    val img: String?, // Made nullable
    @SerialName("media_type")
    val type: String?, // Made nullable if it can be null
    val adult: Boolean,
    @SerialName("original_language")
    val language: String?, // Made nullable
    @SerialName("genre_ids")
    val genres: List<Int>?, // Made nullable if list itself can be null
    val popularity: Double,
    @SerialName("first_air_date")
    val firstAirDate: String?, // Made nullable
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int,
    @SerialName("origin_country")
    val country: List<String>? // Made nullable if list itself can be null
)

@Serializable
data class Tv(
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
    val firstAirDate: String?, // Made nullable
    @SerialName("vote_average")
    val votesAvg: Double,
    @SerialName("vote_count")
    val votes: Int,
    @SerialName("origin_country")
    val country: List<String>? // Made nullable
)

@Serializable
data class Results( // Used for AllTrending (can be movie or TV)
    @SerialName("backdrop_path")
    val backgroundImg: String?, // Made nullable
    val id: Int,
    val title: String?, // Made nullable (for movies)
    @SerialName("original_title")
    val title2: String?, // Made nullable
    val overview: String?, // Made nullable
    @SerialName("poster_path")
    val img: String?, // Made nullable
    @SerialName("media_type")
    val type: String?, // Made nullable (media_type is usually present, but for safety)
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
    @SerialName("vote_count")
    val votes: Int
)

@Serializable
data class Movie(
    @SerialName("backdrop_path")
    val backgroundImg: String?, // Made nullable
    val id: Int,
    val title: String?, // Made nullable
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
    @SerialName("vote_count")
    val votes: Int
)