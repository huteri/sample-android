package me.huteri.seekmax.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("displayname")
    val displayname: String?
)