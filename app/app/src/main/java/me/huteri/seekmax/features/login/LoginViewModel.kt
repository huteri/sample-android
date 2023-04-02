package me.huteri.seekmax.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.huteri.seekmax.data.repositories.UserRepository
import me.huteri.seekmax.model.User
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
    }

    // TODO Add generic error handler
    fun login(username: String, password: String) {

        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            userRepository.login(username, password)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            authToken = this.data
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = this.errorBody?.string()
                        )
                    }
                }
                .onException {
                    _state.update { it.copy(isLoading = false, error = this.message) }
                }

        }
    }

    data class LoginState(
        val isLoading: Boolean = false,
        val authToken: String? = null,
        val error: String? = null
    )
}