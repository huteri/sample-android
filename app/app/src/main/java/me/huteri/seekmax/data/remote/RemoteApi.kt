package me.huteri.seekmax.data.remote

import me.huteri.seekmax.data.remote.model.request.LoginRequest
import me.huteri.seekmax.data.remote.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface RemoteApi {

    @POST("auth")
    suspend fun login(@Body loginRequest: LoginRequest): String
}