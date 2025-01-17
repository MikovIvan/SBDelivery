package ru.mikov.sbdelivery.data.remote.interceptors

import com.squareup.moshi.JsonEncodingException
import okhttp3.Interceptor
import okhttp3.Response
import ru.mikov.sbdelivery.data.JsonConverter.moshi
import ru.mikov.sbdelivery.data.remote.err.ApiError
import ru.mikov.sbdelivery.data.remote.err.ErrorBody

class ErrorStatusInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val res = chain.proceed(chain.request())

        if (res.isSuccessful) return res

        val errMessage = try {
            moshi.adapter(ErrorBody::class.java).fromJson(res.body!!.string())?.message
        } catch (e: JsonEncodingException) {
            e.message
        }

        when (res.code) {
            400 -> throw ApiError.BadRequest(errMessage)
            401 -> throw ApiError.Unauthorized(errMessage)
            403 -> throw ApiError.Forbidden(errMessage)
            404 -> throw ApiError.NotFound(errMessage)
            500 -> throw ApiError.InternalServerError(errMessage)
            else -> throw ApiError.UnknownError(errMessage)
        }
    }
}