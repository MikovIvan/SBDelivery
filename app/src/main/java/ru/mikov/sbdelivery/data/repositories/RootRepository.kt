package ru.mikov.sbdelivery.data.repositories

import androidx.lifecycle.LiveData
import ru.mikov.sbdelivery.data.local.PrefManager

object RootRepository {
    val preferences = PrefManager
    fun isAuth(): LiveData<Boolean> = preferences.isAuth
    fun setAuth(auth: Boolean) = preferences.setAuth(auth)
}
