package com.example.maverickshows.app.actor.domain

import com.example.maverickshows.app.core.models.Actor

data class ActorData(
    val name: String,
    val biography: String,
    val gender: String,
    val id: String,
    val department: String,
    val birthPlace: String,
    val profile: String,
    val birthday: String
)

fun Actor.toActorData() = ActorData(
    name = name,
    biography = biography.toString(),
    gender = when (gender) {
        0 -> "Not Set"
        1 -> "Female"
        2 -> "Male"
        else -> "Non-Binary"
    },
    id = id.toString(),
    department = department.toString(),
    birthPlace = birthPlace.toString(),
    profile = profile.toString(),
    birthday = birthday.toString()
)