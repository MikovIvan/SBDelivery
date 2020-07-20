package ru.mikov.sbdelivery.data.local

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import ru.mikov.sbdelivery.App
import ru.mikov.sbdelivery.data.delegates.PrefLiveDataDelegate

object PrefManager {
    internal val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.applicationContext())
    }

    val isAuth by PrefLiveDataDelegate(false)

    fun clearAll() {
        preferences.edit()
            .clear()
            .apply()
    }

    fun setAuth(auth: Boolean) {
        preferences.edit().apply {
            putBoolean(::isAuth.name, auth)
            apply()
        }
    }
}
