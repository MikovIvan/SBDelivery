package ru.mikov.sbdelivery.data.local

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.preference.PreferenceManager
import ru.mikov.sbdelivery.App
import ru.mikov.sbdelivery.data.JsonConverter.moshi
import ru.mikov.sbdelivery.data.delegates.PrefDelegate
import ru.mikov.sbdelivery.data.delegates.PrefLiveDelegate
import ru.mikov.sbdelivery.data.delegates.PrefLiveObjDelegate
import ru.mikov.sbdelivery.data.delegates.PrefObjDelegate
import ru.mikov.sbdelivery.data.models.User

object PrefManager {
    internal val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.applicationContext())
    }

    var accessToken by PrefDelegate("")
    var refreshToken by PrefDelegate("")
    var profile: User? by PrefObjDelegate(moshi.adapter(User::class.java))

    val isAuthLive: LiveData<Boolean> by lazy {
        val token by PrefLiveDelegate("accessToken", "", preferences)
        token.map { it.isNotEmpty() }
    }

    val profileLive: LiveData<User?> by PrefLiveObjDelegate("profile", moshi.adapter(User::class.java), preferences)

    fun clearAll() {
        preferences.edit()
            .clear()
            .apply()
    }
}
