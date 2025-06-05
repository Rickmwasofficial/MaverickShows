package com.example.maverickshows.app.actor.data

import com.example.maverickshows.app.actor.domain.ActorData
import com.example.maverickshows.app.actor.domain.FilmographyData
import com.example.maverickshows.app.actor.domain.toActorData
import com.example.maverickshows.app.actor.domain.toFilmography
import com.example.maverickshows.app.core.models.ActorImages
import com.example.maverickshows.app.core.models.Genre
import com.example.maverickshows.app.core.models.ImageData
import com.example.maverickshows.app.core.network.TmdbAPI

class ActorDataRepImpl(
   private val api: TmdbAPI
): ActorDataRep {
    override suspend fun getActorData(id: String): ActorData {
        return api.getPersonDetails(id = id).toActorData()
    }

    override suspend fun getActorImages(id: String): ActorImages {
        return api.getActorImages(id = id)
    }

    override suspend fun getFilmography(id: String): List<FilmographyData> {
        val movie = api.getMovieFilmography(id = id)
        val tv = api.getTvFilmography(id = id)
        return if (movie.cast.isNotEmpty() || tv.cast.isNotEmpty()) {
            movie.cast.map {
                it.toFilmography()
            } + tv.cast.map {
                it.toFilmography()
            }
        } else {
            movie.crew.map {
                it.toFilmography()
            } + tv.crew.map {
                it.toFilmography()
            }
        }

    }

    override suspend fun getGenres(): List<Genre> {
        return (api.getTvGenres().genres.filter { it.name.isNotBlank() } +
                api.getMovieGenres().genres.filter { it.name.isNotBlank() })
    }
}