package ru.mikov.sbdelivery.data.remote.res

import ru.mikov.sbdelivery.data.models.User

data class RegistrationRes(
    val user: User? = null,
    val refreshToken: String,
    val accessToken: String
)