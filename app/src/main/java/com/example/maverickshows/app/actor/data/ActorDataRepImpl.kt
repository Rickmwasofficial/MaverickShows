package com.example.maverickshows.app.actor.data

import com.example.maverickshows.app.actor.domain.ActorData
import com.example.maverickshows.app.actor.domain.toActorData
import com.example.maverickshows.app.core.models.ActorImages
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
}