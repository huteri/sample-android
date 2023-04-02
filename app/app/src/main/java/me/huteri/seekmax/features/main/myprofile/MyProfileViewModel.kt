package me.huteri.seekmax.features.main.myprofile

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
class MyProfileViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(MyProfileViewModel.MyProfileState())
    val state = _state.asStateFlow()

    init {
        if(userRepository.getAuthToken().isNullOrBlank()) {
            _state.update { it.copy(navigateToLogin = true) }
        } else {
            val user =  userRepository.getUser()
            _state.update {
                it.copy(
                    user = user
                )
            }
        }

    }

    fun logout() {
        userRepository.saveAuthToken("")
        _state.update { it.copy(navigateToLogin = true) }
    }

    fun updateName(name: String) {
        _state.update { it.copy(
            isLoading = true
        ) }

        viewModelScope.launch {

            userRepository.updateName(name)
                .onSuccess {
                    _state.update { it.copy(
                        isLoading = false,
                        user = this.data
                    ) }
                }
                .onError {
                    _state.update { it.copy(
                        isLoading = false,
                        message = this.errorBody?.string(),
                    ) }
                }
                .onException {
                    _state.update { it.copy(
                        isLoading = false,
                        message = this.message
                    ) }
                }
        }
    }

    fun messageShown() {
        _state.update { it.copy(
            message = null
        ) }
    }

    data class MyProfileState(
        val isLoading: Boolean = false,
        val navigateToLogin: Boolean = false,
        val user: User? = null,
        val message: String? = null
    )
}