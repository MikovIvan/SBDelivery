package ru.mikov.sbdelivery.data.remote.req

data class RecoveryEmailReq(val email: String)

data class RecoveryCodeReq(val email: String, val code: String)

data class RecoveryPasswordReq(val email: String, val code: String, val password: String)