package ru.mikov.sbdelivery.data.remote.res

data class AuthRes(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val refreshToken: String,
    val accessToken: String
)