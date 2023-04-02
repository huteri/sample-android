package me.huteri.seekmax.features.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.huteri.seekmax.data.repositories.UserRepository
import me.huteri.seekmax.features.login.LoginViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val userRepository: UserRepository): ViewModel() {
    private val _state = MutableStateFlow(MainViewModel.MainState())
    val state = _state.asStateFlow()

    data class MainState(
        val isLoading: Boolean = false,
        val navigateToLogin: Boolean = false
    )
}