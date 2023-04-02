package me.huteri.seekmax.data.remote.model.response

import me.huteri.seekmax.model.Job


data class JobResponse(
    val id: String?,
    val positionTitle: String?,
    val description: String?,
    val company: CompanyResponse?,
    val haveIApplied: Boolean?
) {
    fun toJob() = Job(positionTitle, description, haveIApplied, company?.toCompany())
}