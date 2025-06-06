package com.example.maverickshows.app.details.data

import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.models.ImageData
import com.example.maverickshows.app.core.models.Trailer
import com.example.maverickshows.app.core.network.TmdbAPI
import com.example.maverickshows.app.details.domain.DetailCredits
import com.example.maverickshows.app.details.domain.DetailData
import com.example.maverickshows.app.details.domain.toDetailCredits
import com.example.maverickshows.app.details.domain.toDetailData
import com.example.maverickshows.app.home.domain.HomeData
import com.example.maverickshows.app.home.domain.toHomeData

class DetailDataRepImpl(
    private val api: TmdbAPI
): DetailDataRep {
    override suspend fun getMovieDetail(id: String): DetailData {
        return api.getMovieDetails(id = id).toDetailData()
    }

    override suspend fun getTvDetail(id: String): DetailData {
        return api.getTvDetails(id = id).toDetailData()
    }

    override suspend fun getMovieImages(id: String): ImageData {
        return api.getMovieImages(id = id)
    }

    override suspend fun getTvImages(id: String): ImageData {
        return api.getTvImages(id = id)
    }

    override suspend fun getTvCredits(id: String): List<DetailCredits> {
        return api.getTvCredits(id = id).cast.map {
            it.toDetailCredits()
        } + api.getTvCredits(id = id).crew.map {
            it.toDetailCredits()
        }
    }

    override suspend fun getMovieCredits(id: String): List<DetailCredits> {
        return api.getMovieCredits(id = id).cast.map {
            it.toDetailCredits()
        } + api.getMovieCredits(id = id).crew.map {
            it.toDetailCredits()
        }
    }

    override suspend fun getTvRecommendations(id: String): List<HomeData> {
        return api.getTvRecommendations(id = id).results.map {
            it.toHomeData()
        }
    }

    override suspend fun getMovieRecommendations(id: String): List<HomeData> {
        return api.getMovieRecommendations(id = id).results.map {
            it.toHomeData()
        }
    }

    override suspend fun getGenres(): List<Genre> {
        return (api.getTvGenres().genres.filter { it.id != null && it.name.isNotBlank() } +
                api.getMovieGenres().genres.filter { it.id != null && it.name.isNotBlank() })
    }

    override suspend fun getTvTrailers(id: String): Trailer {
        return api.getTvTrailers(id = id)
    }

    override suspend fun getMovieTrailers(id: String): Trailer {
        return  api.getMovieTrailers(id = id)
    }
}