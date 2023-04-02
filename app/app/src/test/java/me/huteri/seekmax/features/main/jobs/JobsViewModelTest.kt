package me.huteri.seekmax.features.main.jobs

import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import me.huteri.seekmax.data.repositories.JobRepository
import me.huteri.seekmax.data.repositories.JobRepositoryImpl
import me.huteri.seekmax.model.Job
import me.huteri.seekmax.util.MainDispatcherRule
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class JobsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val jobRepository = mockk<JobRepository>()

    lateinit var viewModel: JobsViewModel

    @Before
    fun setUp() {
    }

    @Test
    fun errorShown_setErrorNull() {

        coEvery { jobRepository.getJobs() } returns ApiResponse.error(Exception("test"))

        viewModel = JobsViewModel(jobRepository)
        assertNotNull(viewModel.state.value.error)

        viewModel.errorShown()

        assertNull(viewModel.state.value.error)
    }

    @Test
    fun getJobList_showJobList() {

        val jobList = listOf<Job>()
        coEvery { jobRepository.getJobs() } returns ApiResponse.Success(Response.success(jobList))

        viewModel = JobsViewModel(jobRepository)

        assertEquals(viewModel.state.value.jobs, jobList)

    }
}