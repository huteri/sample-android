package me.huteri.seekmax.features.main

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import me.huteri.seekmax.data.repositories.UserRepository

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = me.huteri.seekmax.features.login.MainDispatcherRule()

    private lateinit var viewModel: MainViewModel

    var userRepository: UserRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
    }

    @Test
    fun init_userNotLoggedIn_navigateToLogin() {

        every { userRepository.getAuthToken() } returns ""

        viewModel = MainViewModel(userRepository)

        assertEquals(viewModel.state.value.navigateToLogin, true)
    }

    @Test
    fun logout_deleteAuthToken_navigateToLogin() {
        every { userRepository.getAuthToken() } returns "authToken"

        viewModel = MainViewModel(userRepository)
        viewModel.logout()

        verify { userRepository.saveAuthToken("") }
        assertEquals(viewModel.state.value.navigateToLogin, true)
    }
}