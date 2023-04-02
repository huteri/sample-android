package me.huteri.seekmax.data.remote.model.response

import com.google.gson.annotations.SerializedName
import me.huteri.seekmax.model.User

data class UserResponse(
    val username: String?,
    @SerializedName("displayname")
    val displayName: String?
) {
    fun toUser(): User {
        return User(username, displayName)
    }
}