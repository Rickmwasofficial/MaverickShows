package com.example.maverickshows.app.home.data

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.domain.HomeData

interface HomeDataRep {
    suspend fun getTrendingMovies(): List<HomeData>
    suspend fun getTrendingTv(): List<HomeData>
    suspend fun getPopularMovies(): List<HomeData>
    suspend fun getUpcomingMovies(): List<HomeData>
    suspend fun getTopRatedMovies(): List<HomeData>
    suspend fun getNowPlayingMovies(): List<HomeData>
    suspend fun getPopularTv(): List<HomeData>
    suspend fun getTopRatedTv(): List<HomeData>
    suspend fun getOnAirTv(): List<HomeData>
    suspend fun getAiringTv(): List<HomeData>
    suspend fun getAllTrending(): List<HomeData>
    suspend fun getAllPopular(): List<HomeData>
    suspend fun getAllTopRated(): List<HomeData>
    suspend fun getAllGenres(): List<Genre>
}