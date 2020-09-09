package ru.mikov.sbdelivery.data.remote.req

data class RegistrationReq(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)