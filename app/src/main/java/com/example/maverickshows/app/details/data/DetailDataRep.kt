package com.example.maverickshows.app.details.data

import com.example.maverickshows.app.details.domain.DetailData

interface DetailDataRep {
    suspend fun getMovieDetail(id: String): DetailData
    suspend fun getTvDetail(id: String): DetailData
}