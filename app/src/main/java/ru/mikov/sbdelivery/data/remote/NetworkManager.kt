package ru.mikov.sbdelivery.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.mikov.sbdelivery.AppConfig
import ru.mikov.sbdelivery.data.JsonConverter.moshi
import ru.mikov.sbdelivery.data.remote.interceptors.ErrorStatusInterceptor
import ru.mikov.sbdelivery.data.remote.interceptors.NetworkStatusInterceptor
import ru.mikov.sbdelivery.data.remote.interceptors.TokenAuthenticator
import java.util.concurrent.TimeUnit

object NetworkManager {
    val api: RestService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient().newBuilder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .authenticator(TokenAuthenticator())
            .addInterceptor(NetworkStatusInterceptor())
            .addInterceptor(logging)
            .addInterceptor(ErrorStatusInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(AppConfig.BASE_URL)
            .build()

        retrofit.create(RestService::class.java)

    }
}