package com.example.maverickshows.app.core.network

import com.example.maverickshows.BuildConfig
import com.example.maverickshows.app.core.models.AllTrending
import com.example.maverickshows.app.core.models.NowPlayingAndUpcomingMovies
import com.example.maverickshows.app.core.models.PopularAndTopRatedMovies
import com.example.maverickshows.app.core.models.TrendingTv
import com.example.maverickshows.app.core.models.Tv
import com.example.maverickshows.app.core.models.TvData
import retrofit2.http.GET
import retrofit2.http.Query

private const val APIKEY = BuildConfig.TMDB_API

interface TmdbAPI {
    @GET("trending/all/week")
    suspend fun getAllTrending(
        @Query("language") lang: String = "en-US",
        @Query("api_key") key: String = APIKEY
    ): AllTrending

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("language") lang: String = "en-US",
        @Query("api_key") key: String = APIKEY
    ): AllTrending

    @GET("trending/tv/week")
    suspend fun getTrendingTv(
        @Query("language") lang: String = "en-US",
        @Query("api_key") key: String = APIKEY
    ): TrendingTv

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1",
        @Query("api_key") key: String = APIKEY
    ): NowPlayingAndUpcomingMovies

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1",
        @Query("api_key") key: String = APIKEY
    ): PopularAndTopRatedMovies

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1",
        @Query("api_key") key: String = APIKEY
    ): PopularAndTopRatedMovies

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1",
        @Query("api_key") key: String = APIKEY
    ): NowPlayingAndUpcomingMovies

    @GET("tv/airing_today")
    suspend fun getAiringTodayTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1",
        @Query("api_key") key: String = APIKEY
    ): TvData

    @GET("tv/on_the_air")
    suspend fun getOnAirTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1",
        @Query("api_key") key: String = APIKEY
    ): TvData

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1",
        @Query("api_key") key: String = APIKEY
    ): TvData

    @GET("tv/top_rated")
    suspend fun getTopRatedTv(
        @Query("language") lang: String = "en-US",
        @Query("page") page: String = "1",
        @Query("api_key") key: String = APIKEY
    ): TvData
}