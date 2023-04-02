package me.huteri.seekmax.data.repositories

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnSuccess
import me.huteri.seekmax.data.local.PreferencesManager
import me.huteri.seekmax.data.remote.RemoteApi
import me.huteri.seekmax.data.remote.model.request.LoginRequest
import me.huteri.seekmax.data.remote.model.request.UserRequest
import me.huteri.seekmax.data.remote.model.response.UserResponse
import me.huteri.seekmax.model.User
import javax.inject.Inject

interface UserRepository {
    suspend fun login(username: String, password: String): ApiResponse<String>
    suspend fun updateName(name: String): ApiResponse<User>
    fun saveAuthToken(authToken: String)
    fun getAuthToken(): String?
    fun getUser(): User?
}

class UserRepositoryImpl @Inject constructor(
    val remoteApi: RemoteApi,
    val preferencesManager: PreferencesManager
) : UserRepository {
    override suspend fun login(username: String, password: String): ApiResponse<String> {
        return remoteApi.login(LoginRequest(username, password)).suspendOnSuccess {
            saveAuthToken(this.data)
        }
    }

    override suspend fun updateName(name: String): ApiResponse<User> {
        return remoteApi.updateName(UserRequest(name)).suspendMapSuccess {
            toUser()
        }.suspendOnSuccess {
            preferencesManager.user = this.data
        }
    }

    override fun saveAuthToken(authToken: String) {
        preferencesManager.authToken = authToken
    }

    override fun getAuthToken(): String? {
        return preferencesManager.authToken
    }

    override fun getUser(): User? {
        return preferencesManager.user
    }
}