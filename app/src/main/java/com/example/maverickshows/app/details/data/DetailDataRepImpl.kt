package com.example.maverickshows.app.details.data

import com.example.maverickshows.app.core.network.TmdbAPI
import com.example.maverickshows.app.details.domain.DetailData
import com.example.maverickshows.app.details.domain.toDetailData

class DetailDataRepImpl(
    private val api: TmdbAPI
): DetailDataRep {
    override suspend fun getMovieDetail(id: String): DetailData {
        return api.getMovieDetails(id = id).toDetailData()
    }

    override suspend fun getTvDetail(id: String): DetailData {
        return api.getTvDetails(id = id).toDetailData()
    }
}