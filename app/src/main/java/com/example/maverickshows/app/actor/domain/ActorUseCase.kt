package com.example.maverickshows.app.actor.domain

import com.example.maverickshows.app.actor.data.ActorDataRepImpl
import com.example.maverickshows.app.core.models.ActorImages
import com.example.maverickshows.app.core.models.Genre
import javax.inject.Inject

data class ActorUseCase(
    val getActorData: GetActorData,
    val getActorImages: GetActorImages,
    val getActorFilmography: GetActorFilmography,
    val getGenres: GetGenres
)

data class FullActorData(
    val actorData: ActorData,
    val actorImages: ActorImages,
    val actorFilmography: List<FilmographyData>,
    val genre: List<Genre>
)

class GetAllActorData @Inject constructor(
    private val actorUseCase: ActorUseCase
) {
    suspend operator fun invoke(id: String): FullActorData {
        return FullActorData(
            actorData = actorUseCase.getActorData(id),
            actorImages = actorUseCase.getActorImages(id),
            actorFilmography = actorUseCase.getActorFilmography(id),
            genre = actorUseCase.getGenres()
        )
    }
}

class GetActorData @Inject constructor(
    private val actorDataRepImpl: ActorDataRepImpl
) {
    suspend operator fun invoke(id: String): ActorData {
        return actorDataRepImpl.getActorData(id)
    }
}

class GetActorImages @Inject constructor(
    private val actorDataRepImpl: ActorDataRepImpl
) {
    suspend operator fun invoke(id: String): ActorImages {
        return actorDataRepImpl.getActorImages(id)
    }
}

class GetActorFilmography @Inject constructor(
    private val actorDataRepImpl: ActorDataRepImpl
) {
    suspend operator fun invoke(id: String): List<FilmographyData> {
        return actorDataRepImpl.getFilmography(id)
    }
}

class GetGenres @Inject constructor(
    private val actorDataRepImpl: ActorDataRepImpl
) {
    suspend operator fun invoke(): List<Genre> {
        return actorDataRepImpl.getGenres()
    }
}