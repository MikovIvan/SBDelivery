package ru.mikov.sbdelivery.data.local

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.preference.PreferenceManager
import ru.mikov.sbdelivery.App
import ru.mikov.sbdelivery.data.delegates.PrefDelegate
import ru.mikov.sbdelivery.data.delegates.PrefLiveDelegate
import ru.mikov.sbdelivery.data.models.User

object PrefManager {
    internal val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.applicationContext())
    }

    var accessToken by PrefDelegate("")
    var refreshToken by PrefDelegate("")
    var profile: User? = null

    val isAuthLive: LiveData<Boolean> by lazy {
        val token by PrefLiveDelegate("accessToken", "", preferences)
        token.map { it.isNotEmpty() }
    }

    fun clearAll() {
        preferences.edit()
            .clear()
            .apply()
    }
}
