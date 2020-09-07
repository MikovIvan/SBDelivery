package ru.mikov.sbdelivery.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.mikov.sbdelivery.AppConfig
import ru.mikov.sbdelivery.data.remote.interceptors.ErrorStatusInterceptor
import ru.mikov.sbdelivery.data.remote.interceptors.NetworkStatusInterceptor
import java.util.concurrent.TimeUnit

object NetworkManager {
    val api: RestService by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient().newBuilder()
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
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