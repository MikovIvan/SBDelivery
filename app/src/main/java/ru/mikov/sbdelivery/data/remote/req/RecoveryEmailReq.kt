package ru.mikov.sbdelivery.data.remote.req

data class RecoveryEmailReq(val email: String)

data class RecoveryCodeReq(val email: String, val code: Int)

data class RecoveryPasswordReq(val email: String, val code: Int, val password: String)