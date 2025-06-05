package com.example.maverickshows.app.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val adult: Boolean = true,
    @SerialName("also_known_as")
    val aka: List<String>,
    val biography: String?,
    val birthday: String?,
    val deathday: String?,
    val gender: Int,
    val homepage: String?,
    val id: Int,
    @SerialName("imdb_id")
    val imdb: String?,
    @SerialName("known_for_department")
    val department: String?,
    val name: String,
    @SerialName("place_of_birth")
    val birthPlace: String?,
    val popularity: Double,
    @SerialName("profile_path")
    val profile: String?
)

@Serializable
data class ActorImages(
    val id: Int,
    val profiles: List<Backdrop>
)
