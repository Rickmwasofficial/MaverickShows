package com.example.maverickshows.app.details.data

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.models.ImageData
import com.example.maverickshows.app.core.models.Trailer
import com.example.maverickshows.app.details.domain.DetailCredits
import com.example.maverickshows.app.details.domain.DetailData
import com.example.maverickshows.app.home.domain.HomeData

interface DetailDataRep {
    suspend fun getMovieDetail(id: String): DetailData
    suspend fun getTvDetail(id: String): DetailData
    suspend fun getMovieImages(id: String): ImageData
    suspend fun getTvImages(id: String): ImageData
    suspend fun getTvCredits(id: String): List<DetailCredits>
    suspend fun getMovieCredits(id: String): List<DetailCredits>
    suspend fun getTvRecommendations(id: String): List<HomeData>
    suspend fun getMovieRecommendations(id: String): List<HomeData>
    suspend fun getGenres(): List<Genre>
    suspend fun getTvTrailers(id: String): Trailer
    suspend fun getMovieTrailers(id: String): Trailer
}