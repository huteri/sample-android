package me.huteri.seekmax.data.repositories

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import me.huteri.seekmax.data.local.PreferencesManager
import me.huteri.seekmax.data.remote.RemoteApi
import me.huteri.seekmax.data.remote.model.request.LoginRequest
import me.huteri.seekmax.model.User
import javax.inject.Inject

interface UserRepository {
    suspend fun login(username: String, password: String): ApiResponse<String>
    fun saveAuthToken(authToken: String)
    fun getAuthToken(): String?
}

class UserRepositoryImpl @Inject constructor(val remoteApi: RemoteApi, val preferencesManager: PreferencesManager) : UserRepository {
    override suspend fun login(username: String, password: String): ApiResponse<String> {
        return remoteApi.login(LoginRequest(username, password)).suspendOnSuccess {
            saveAuthToken(this.data)
        }
    }

    override fun saveAuthToken(authToken: String) {
        preferencesManager.authToken = authToken
    }

    override fun getAuthToken(): String? {
        return preferencesManager.authToken
    }
}