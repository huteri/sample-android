package me.huteri.seekmax.features.main.myprofile

import com.skydoves.sandwich.ApiResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import me.huteri.seekmax.data.repositories.UserRepository
import me.huteri.seekmax.features.main.MainViewModel
import me.huteri.seekmax.model.User
import me.huteri.seekmax.util.MainDispatcherRule
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class MyProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MyProfileViewModel

    var userRepository: UserRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
    }

    @Test
    fun init_userNotLoggedIn_navigateToLogin() {

        every { userRepository.getAuthToken() } returns ""

        viewModel = MyProfileViewModel(userRepository)

        TestCase.assertEquals(viewModel.state.value.navigateToLogin, true)
    }

    @Test
    fun logout_deleteAuthToken_navigateToLogin() {
        every { userRepository.getAuthToken() } returns "authToken"

        viewModel = MyProfileViewModel(userRepository)
        viewModel.logout()

        verify { userRepository.saveAuthToken("") }
        TestCase.assertEquals(viewModel.state.value.navigateToLogin, true)
    }

    @Test
    fun updateName_success_returns_user() {
        val name = "name"
        val newUser = mockk<User>();

        coEvery { userRepository.updateName(name) } returns ApiResponse.Success(Response.success(newUser))
        viewModel = MyProfileViewModel(userRepository)
        viewModel.updateName(name)
        TestCase.assertEquals(newUser, viewModel.state.value.user)
    }

    @Test
    fun updateName_failure_showError() {
        val error = "error messsage"
        val name = "name"

        coEvery { userRepository.updateName(name) } returns ApiResponse.error(Throwable(error))
        viewModel = MyProfileViewModel(userRepository)
        viewModel.updateName(name)
        TestCase.assertEquals(error, viewModel.state.value.message)
    }

}