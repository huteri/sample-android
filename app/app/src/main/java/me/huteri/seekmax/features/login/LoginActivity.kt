package me.huteri.seekmax.features.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.huteri.seekmax.features.main.MainActivity
import me.huteri.seekmax.ui.theme.SeekMaxTheme


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SeekMaxTheme {
                val viewModel = hiltViewModel<LoginViewModel>()
                val state by viewModel.state.collectAsState()
                LoginScreen(state, viewModel::login, ::navigateToMain)
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}