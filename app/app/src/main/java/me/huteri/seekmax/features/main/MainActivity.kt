package me.huteri.seekmax.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.huteri.seekmax.ui.theme.SeekMaxTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SeekMaxTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                val state by viewModel.state.collectAsState()
                MainScreen(state)
            }
        }
    }
}
