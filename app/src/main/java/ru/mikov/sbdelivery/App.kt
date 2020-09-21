package ru.mikov.sbdelivery

import android.app.Application
import android.content.Context
import android.os.Build
import com.facebook.stetho.Stetho
import ru.mikov.sbdelivery.data.remote.NetworkMonitor

class App : Application() {

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        //start network monitoring
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkMonitor.registerNetworkMonitor(applicationContext())
        }

        Stetho.initializeWithDefaults(this)
    }
}