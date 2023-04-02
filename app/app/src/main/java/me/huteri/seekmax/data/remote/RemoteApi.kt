package me.huteri.seekmax.data.remote

import com.skydoves.sandwich.ApiResponse
import me.huteri.seekmax.data.remote.model.request.LoginRequest
import me.huteri.seekmax.data.remote.model.request.UserRequest
import me.huteri.seekmax.data.remote.model.response.JobListResponse
import me.huteri.seekmax.data.remote.model.response.JobResponse
import me.huteri.seekmax.data.remote.model.response.UserResponse
import retrofit2.http.*

interface RemoteApi {

    @POST("auth")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResponse<String>

    @GET("jobs")
    suspend fun getJobs(@Query("per_page") perPage: Int = 10, @Query("page") page: Int = 1 ) : ApiResponse<JobListResponse>

    @POST("user")
    suspend fun updateName(@Body user: UserRequest): ApiResponse<UserResponse>
}