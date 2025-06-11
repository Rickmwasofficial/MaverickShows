package com.example.maverickshows.app.home.data

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.home.domain.HomeData

interface HomeDataRep {
    suspend fun getTrendingMovies(page: String = "1"): List<HomeData>
    suspend fun getTrendingTv(page: String = "1"): List<HomeData>
    suspend fun getPopularMovies(page: String = "1"): List<HomeData>
    suspend fun getUpcomingMovies(page: String = "1"): List<HomeData>
    suspend fun getTopRatedMovies(page: String = "1"): List<HomeData>
    suspend fun getNowPlayingMovies(page: String = "1"): List<HomeData>
    suspend fun getPopularTv(page: String = "1"): List<HomeData>
    suspend fun getTopRatedTv(page: String = "1"): List<HomeData>
    suspend fun getOnAirTv(page: String = "1"): List<HomeData>
    suspend fun getAiringTv(page: String = "1"): List<HomeData>
    suspend fun getAllTrending(page: String = "1"): List<HomeData>
    suspend fun getAllPopular(page: String = "1"): List<HomeData>
    suspend fun getAllTopRated(page: String = "1"): List<HomeData>
    suspend fun getAllGenres(): List<Genre>
}