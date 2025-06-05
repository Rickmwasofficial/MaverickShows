package com.example.maverickshows.app.actor.data

import com.example.maverickshows.app.actor.domain.ActorData
import com.example.maverickshows.app.actor.domain.FilmographyData
import com.example.maverickshows.app.core.models.ActorImages
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.models.ImageData

interface ActorDataRep {
    suspend fun getActorData(id: String): ActorData
    suspend fun getActorImages(id: String): ActorImages
    suspend fun getFilmography(id: String): List<FilmographyData>
    suspend fun getGenres(): List<Genre>
}