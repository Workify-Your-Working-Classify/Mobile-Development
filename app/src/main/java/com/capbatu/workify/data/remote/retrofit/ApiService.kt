package com.capbatu.workify.data.remote.retrofit

import com.capbatu.workify.data.remote.request.ActivityRequest
import com.capbatu.workify.data.remote.request.LoginRequest
import com.capbatu.workify.data.remote.request.RegisterRequest
import com.capbatu.workify.data.remote.response.ActivityResponse
import com.capbatu.workify.data.remote.response.ArticleResponse
import com.capbatu.workify.data.remote.response.LoginResponse
import com.capbatu.workify.data.remote.response.LogoutResponse
import com.capbatu.workify.data.remote.response.RegisterResponse
import com.capbatu.workify.data.remote.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest,
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest,
    ): RegisterResponse

    @GET("{uid}")
    suspend fun getUser(
        @Path("uid") uid: String,
    ): UserResponse

    @POST("logout")
    suspend fun logout(): LogoutResponse

    @POST("user/{userId}/kegiatan")
    suspend fun postActivity(
        @Path("userId") uid: String,
        @Body request: ActivityRequest,
    )

    @GET("user/{uid}/kegiatan")
    suspend fun getActivity(
        @Path("uid") uid: String,
    ): List<ActivityResponse>

    @GET("articles")
    suspend fun getArticle(): List<ArticleResponse>
}
