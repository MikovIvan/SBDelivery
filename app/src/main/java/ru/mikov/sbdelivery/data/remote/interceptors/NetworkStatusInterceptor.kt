package ru.mikov.sbdelivery.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.mikov.sbdelivery.data.remote.NetworkMonitor
import ru.mikov.sbdelivery.data.remote.err.NoNetworkError

class NetworkStatusInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // return response or throw error
        if (!NetworkMonitor.isConnected) throw NoNetworkError()
        return chain.proceed(chain.request())
    }
}