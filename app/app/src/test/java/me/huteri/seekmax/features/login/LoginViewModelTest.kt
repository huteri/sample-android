package me.huteri.seekmax.features.login

import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import me.huteri.seekmax.data.repositories.UserRepository
import me.huteri.seekmax.util.MainDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel

    var userRepository: UserRepository = mockk()

    @Before
    fun setUp() {
         viewModel = LoginViewModel(userRepository)
    }

    @Test
    fun login_success_returns_auth_token() {
        val username = "username"
        val password = "password"
        val authToken = "authToken"

        coEvery { userRepository.login(username, password) } returns ApiResponse.Success(Response.success(authToken))

        viewModel.login(username, password)
        assertEquals(authToken, viewModel.state.value.authToken)
    }

    @Test
    fun login_failed_shows_error_message() {
        val username = "username"
        val password = "password"
        val message = "login failed"

        coEvery { userRepository.login(username, password) } returns ApiResponse.error(Exception(message))

        viewModel.login(username, password)
        assertEquals(message, viewModel.state.value.error)
    }
}