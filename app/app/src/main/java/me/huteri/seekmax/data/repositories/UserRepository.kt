package me.huteri.seekmax.data.repositories

import me.huteri.seekmax.data.local.PreferencesManager
import me.huteri.seekmax.data.remote.RemoteApi
import me.huteri.seekmax.data.remote.model.request.LoginRequest
import me.huteri.seekmax.model.User
import javax.inject.Inject

interface UserRepository {
    suspend fun login(username: String, password: String): String
    fun saveAuthToken(authToken: String)
    fun getAuthToken(): String?
}

class UserRepositoryImpl @Inject constructor(val remoteApi: RemoteApi, val preferencesManager: PreferencesManager) : UserRepository {
    override suspend fun login(username: String, password: String): String {
        return remoteApi.login(LoginRequest(username, password))
    }

    override fun saveAuthToken(authToken: String) {
        preferencesManager.authToken = authToken
    }

    override fun getAuthToken(): String? {
        return preferencesManager.authToken
    }
}