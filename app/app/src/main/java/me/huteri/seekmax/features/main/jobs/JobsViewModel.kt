package me.huteri.seekmax.features.main.jobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.huteri.seekmax.data.repositories.JobRepository
import me.huteri.seekmax.model.Job
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(val jobRepository: JobRepository) : ViewModel(){
    private val _state = MutableStateFlow(JobsViewModel.JobsState())
    val state = _state.asStateFlow()

    init {
        getJobList()
    }

    private fun getJobList() {
        _state.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            //TODO do pagination
            jobRepository.getJobs()
                .onSuccess {
                    _state.update{
                        it.copy(
                            isLoading = false,
                            jobs = this.data
                        )
                    }
                }.onError {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = this.errorBody?.string()
                        )
                    }
                }.onException {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = this.message
                        )
                    }
                }
        }
    }

    fun errorShown() {
        _state.update {
            it.copy(
                error = null
            )
        }
    }

    data class JobsState(
        val isLoading: Boolean = false,
        val jobs: List<Job>? = null,
        val error: String? = null

    )
}