package com.example.maverickshows.app.core.network

import com.example.maverickshows.BuildConfig
import com.example.maverickshows.app.core.models.AllTrending
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.models.Genres
import com.example.maverickshows.app.core.models.NowPlayingAndUpcomingMovies
import com.example.maverickshows.app.core.models.PopularAndTopRatedMovies
import com.example.maverickshows.app.core.models.TrendingTv
import com.example.maverickshows.app.core.models.TvData
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/all/week")
    suspend fun getAllTrending(
        @Query("language") lang: String = "en-US"
    ): AllTrending

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("language") lang: String = "en-US"
    ): AllTrending

    @GET("trending/tv/week")
    suspend fun getTrendingTv(
        @Query("language") lang: String = "en-US"
    ): TrendingTv

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1"
    ): NowPlayingAndUpcomingMovies

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1"
    ): PopularAndTopRatedMovies

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1"
    ): PopularAndTopRatedMovies

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1"
    ): NowPlayingAndUpcomingMovies

    @GET("tv/airing_today")
    suspend fun getAiringTodayTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1"
    ): TvData

    @GET("tv/on_the_air")
    suspend fun getOnAirTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1"
    ): TvData

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1"
    ): TvData

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1"
    ): TvData

    @GET("genre/tv/list")
    suspend fun getTvGenres(
        @Query("language") lang: String = "en"
    ): Genres

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") lang: String = "en"
    ): Genres
}