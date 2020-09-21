package ru.mikov.sbdelivery.data.delegates

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import ru.mikov.sbdelivery.data.local.PrefManager
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T) {
    private var storedValue: T? = null


    operator fun provideDelegate(
        thisRef: PrefManager,
        prop: KProperty<*>
    ): ReadWriteProperty<PrefManager, T> {
        val key = prop.name
        return object : ReadWriteProperty<PrefManager, T> {
            override fun getValue(thisRef: PrefManager, property: KProperty<*>): T {
                if (storedValue == null) {
                    @Suppress("UNCHECKED_CAST")
                    storedValue = when (defaultValue) {
                        is Boolean -> thisRef.preferences.getBoolean(
                            key,
                            defaultValue as Boolean
                        ) as T
                        is Int -> thisRef.preferences.getInt(key, defaultValue as Int) as T
                        is Float -> thisRef.preferences.getFloat(key, defaultValue as Float) as T
                        is Long -> thisRef.preferences.getLong(key, defaultValue as Long) as T
                        is String -> thisRef.preferences.getString(key, defaultValue as String) as T
                        else -> error("This type can not be stored into Preferences")
                    }
                }
                return storedValue!!
            }

            override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T) {
                with(thisRef.preferences.edit()) {
                    when (value) {
                        is Boolean -> putBoolean(key, value)
                        is Int -> putInt(key, value)
                        is Float -> putFloat(key, value)
                        is Long -> putLong(key, value)
                        is String -> putString(key, value)
                        else -> error("Only promitive types can be stored into Preferences")
                    }
                    apply()
                }
                storedValue = value
            }
        }
    }
}

class PrefLiveDataDelegate<T>(private val defaultValue: T) :
    ReadOnlyProperty<PrefManager, LiveData<T>> {
    private lateinit var value: LiveData<T>
    override fun getValue(thisRef: PrefManager, property: KProperty<*>): LiveData<T> {
        return if (!::value.isInitialized) PrefLiveData(thisRef, property.name, defaultValue)
        else value
    }

}

@Suppress("UNCHECKED_CAST")
class PrefLiveData<T>(
    prefManager: PrefManager,
    private val key: String,
    private val defaultValue: T
) : LiveData<T>() {

    private val preferences = prefManager.preferences
    private val prefChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, changeKey ->
            if (changeKey == key) {
                val newValue = readValue()
                if (newValue != value) value = newValue
            }
        }

    override fun onActive() {
        super.onActive()
        value = readValue()
        preferences.registerOnSharedPreferenceChangeListener(prefChangeListener)
    }

    override fun onInactive() {
        super.onInactive()
        preferences.unregisterOnSharedPreferenceChangeListener(prefChangeListener)
    }

    private fun readValue(): T = when (defaultValue) {
        is Boolean -> preferences.getBoolean(key, defaultValue) as T
        is String -> preferences.getString(key, defaultValue) as T
        is Float -> preferences.getFloat(key, defaultValue) as T
        is Int -> preferences.getInt(key, defaultValue) as T
        is Long -> preferences.getLong(key, defaultValue) as T
        else -> throw IllegalArgumentException("Value must be primitive type")
    }
}