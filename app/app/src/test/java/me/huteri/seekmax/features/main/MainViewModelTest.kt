package me.huteri.seekmax.features.main

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import me.huteri.seekmax.data.repositories.UserRepository
import me.huteri.seekmax.util.MainDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MainViewModel

    var userRepository: UserRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
    }

    @Test
    fun init_userNotLoggedIn_navigateToLogin() {

        every { userRepository.getAuthToken() } returns ""

        viewModel = MainViewModel(userRepository)

        TestCase.assertEquals(viewModel.state.value.navigateToLogin, true)
    }
}