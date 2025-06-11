package com.example.maverickshows.app.details.domain

import com.example.maverickshows.app.core.data.FavoritesEntity
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.models.ImageData
import com.example.maverickshows.app.core.models.Trailer
import com.example.maverickshows.app.core.models.TrailerVideo
import com.example.maverickshows.app.details.data.DetailDataRepImpl
import com.example.maverickshows.app.home.domain.HomeData
import jakarta.inject.Inject

data class DetailUseCase (
    val getDetails: GetDetails,
    val getImages: GetImages,
    val getCredits: GetCredits,
    val getRecommendations: GetRecommendations,
    val getGenres: GetGenres,
    val getTrailers: GetTrailers
)

data class ShowData (
    val details: DetailData,
    val images: ImageData,
    val credits: List<DetailCredits>,
    val recommendations: List<HomeData>,
    val genres: List<Genre>,
    val trailers: List<TrailerVideo>
)

class ShowDetail @Inject constructor(
    private val detailUseCase: DetailUseCase
) {
    suspend operator fun invoke(id: String, type: String): ShowData {
        return ShowData(
            details = detailUseCase.getDetails(id, type),
            images = detailUseCase.getImages(id, type),
            credits = detailUseCase.getCredits(id, type),
            recommendations = detailUseCase.getRecommendations(id, type),
            genres = detailUseCase.getGenres(),
            trailers = detailUseCase.getTrailers(id, type).results
        )
    }
}

class GetDetails @Inject constructor(
    private val detailDataRepImpl: DetailDataRepImpl
) {
    suspend operator fun invoke(id: String, type: String): DetailData {
        return if (type == "movie") {
            detailDataRepImpl.getMovieDetail(id)
        } else {
            detailDataRepImpl.getTvDetail(id)
        }
    }
}


class GetImages @Inject constructor(
    private val detailDataRepImpl: DetailDataRepImpl
) {
    suspend operator fun invoke(id: String, type: String): ImageData {
        return if (type == "movie") {
            detailDataRepImpl.getMovieImages(id)
        } else {
            detailDataRepImpl.getTvImages(id)
        }
    }
}

class GetTrailers @Inject constructor(
    private val detailDataRepImpl: DetailDataRepImpl
) {
    suspend operator fun invoke(id: String, type: String): Trailer {
        return if (type == "movie") {
            detailDataRepImpl.getMovieTrailers(id)
        } else {
            detailDataRepImpl.getTvTrailers(id)
        }
    }
}

class GetCredits @Inject constructor(
    private val detailDataRepImpl: DetailDataRepImpl
) {
    suspend operator fun invoke(id: String, type: String): List<DetailCredits> {
        return if (type == "movie") {
            detailDataRepImpl.getMovieCredits(id)
        } else {
            detailDataRepImpl.getTvCredits(id)
        }
    }
}

class GetRecommendations @Inject constructor(
    private val detailDataRepImpl: DetailDataRepImpl
) {
    suspend operator fun invoke(id: String, type: String): List<HomeData> {
        return if (type == "movie") {
            detailDataRepImpl.getMovieRecommendations(id)
        } else {
            detailDataRepImpl.getTvRecommendations(id)
        }
    }
}

class GetGenres @Inject constructor(
    private val detailDataRepImpl: DetailDataRepImpl
) {
    suspend operator fun invoke(): List<Genre> {
        return detailDataRepImpl.getGenres()
    }
}

class LikeUseCase @Inject constructor(
    private val repository: DetailDataRepImpl
) {
    suspend operator fun invoke(state: Boolean, item: FavoritesEntity) {
        if (state) {
            repository.insertLikedItem(item = item)
        } else {
            repository.deleteLikedItem(item = item)
        }
    }
}