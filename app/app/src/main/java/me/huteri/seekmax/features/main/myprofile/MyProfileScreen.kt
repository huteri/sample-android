package me.huteri.seekmax.features.main.myprofile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.huteri.seekmax.features.utils.NavigationUtils
import me.huteri.seekmax.ui.theme.PrimaryColor

@Composable
fun ProfileScreen() {
    val viewModel = hiltViewModel<MyProfileViewModel>()
    val state by viewModel.state.collectAsState()

    if (state.navigateToLogin) {
        NavigationUtils.navigateToLogin(LocalContext.current)
    }

    if(state.message != null) {
        Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
        viewModel.messageShown()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            val name = remember { mutableStateOf(state.user?.displayName ?: "") }

            if(state.isLoading) {
                CircularProgressIndicator()
            } else {
                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") },
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.updateName(name.value)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColor),
                ) {
                    Text(text = "Save", color = Color.White)
                }
            }

            Button(
                onClick = {
                   viewModel.logout()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColor),
            ) {
                Text(text = "Logout", color = Color.White)
            }
        }
    }
}
