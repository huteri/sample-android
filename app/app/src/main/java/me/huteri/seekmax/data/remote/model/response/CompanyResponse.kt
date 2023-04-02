package me.huteri.seekmax.data.remote.model.response

import me.huteri.seekmax.model.Company


data class CompanyResponse(
    val id: String?,
    val name: String?
) {

    fun toCompany() = Company(id, name)
}