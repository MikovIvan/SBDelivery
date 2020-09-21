package ru.mikov.sbdelivery.data.remote

import retrofit2.Call
import retrofit2.http.*
import ru.mikov.sbdelivery.data.remote.req.*
import ru.mikov.sbdelivery.data.remote.res.AuthRes
import ru.mikov.sbdelivery.data.remote.res.CategoryRes
import ru.mikov.sbdelivery.data.remote.res.DishRes
import ru.mikov.sbdelivery.data.remote.res.RefreshRes


interface RestService {

    @Headers("If-Modified-Since:Wed, 21 Oct 2015 07:28:00 GMT")
    @GET("dishes")
    suspend fun getAllDishes(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<DishRes>

    @Headers("If-Modified-Since:Wed, 21 Oct 2015 07:28:00 GMT")
    @GET("categories")
    suspend fun getAllCategories(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<CategoryRes>

    @POST("auth/register")
    suspend fun register(@Body registrationReq: RegistrationReq): AuthRes

    @POST("auth/login")
    suspend fun login(@Body loginReq: LoginReq): AuthRes

    @POST("auth/recovery/email")
    suspend fun sendRecoveryEmail(@Body recoveryEmailReq: RecoveryEmailReq)

    @POST("auth/recovery/code")
    suspend fun sendRecoveryCode(@Body recoveryCodeReq: RecoveryCodeReq)

    @POST("auth/recovery/password")
    suspend fun sendRecoveryPassword(@Body recoveryPasswordReq: RecoveryPasswordReq)

    @POST("auth/refresh")
    fun refreshAccessToken(@Body refreshReq: RefreshReq): Call<RefreshRes>

}