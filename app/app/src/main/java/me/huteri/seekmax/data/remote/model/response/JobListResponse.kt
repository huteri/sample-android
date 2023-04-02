package me.huteri.seekmax.data.remote.model.response

data class JobListResponse(
    val hasNext: Boolean?,
    val jobs: List<JobResponse>?
)