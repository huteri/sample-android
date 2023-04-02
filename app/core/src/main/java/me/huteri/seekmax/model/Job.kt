package me.huteri.seekmax.model

data class Job(
    val positionTitle: String?,
    val description: String?,
    val haveIApplied: Boolean?,
    val company: Company?
)

data class Company(
    val id: String?,
    val name: String?
)