package ru.mikov.sbdelivery.data.remote.req

data class RegistrationReq(
    val name: String,
    val surname: String,
    val login: String,
    val password: String
)