package ru.mikov.sbdelivery.data.repositories

import androidx.lifecycle.LiveData
import ru.mikov.sbdelivery.data.local.PrefManager
import ru.mikov.sbdelivery.data.models.User
import ru.mikov.sbdelivery.data.remote.NetworkManager
import ru.mikov.sbdelivery.data.remote.req.LoginReq
import ru.mikov.sbdelivery.data.remote.req.RegistrationReq

object RootRepository {
    private val preferences = PrefManager
    private val network = NetworkManager.api

    fun isAuth(): LiveData<Boolean> = preferences.isAuthLive

    suspend fun register(name: String, surname: String, login: String, password: String) {
        val registration = network.register(RegistrationReq(name, surname, login, password))
        preferences.profile =
            User(registration.id, registration.firstName, registration.lastName, registration.email)
        preferences.accessToken = "Bearer ${registration.accessToken}"
        preferences.refreshToken = registration.refreshToken
    }

    suspend fun login(login: String, pass: String) {
        val auth = network.login(LoginReq(login, pass))
        preferences.profile = User(auth.id, auth.firstName, auth.lastName, auth.email)
        preferences.accessToken = "Bearer ${auth.accessToken}"
        preferences.refreshToken = auth.refreshToken
    }
}
