package me.huteri.seekmax.data.repositories

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendMapSuccess
import me.huteri.seekmax.data.remote.RemoteApi
import me.huteri.seekmax.model.Job
import javax.inject.Inject

interface JobRepository {

    suspend fun getJobs() : ApiResponse<List<Job>>
}

class JobRepositoryImpl @Inject constructor(private val remoteApi: RemoteApi) : JobRepository {

    override suspend fun getJobs(): ApiResponse<List<Job>> {

        return remoteApi.getJobs().suspendMapSuccess {
            jobs?.map { it.toJob()} ?: emptyList()
        }

    }

}