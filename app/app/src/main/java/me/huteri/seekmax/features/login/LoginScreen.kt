package me.huteri.seekmax.features.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    state: LoginViewModel.LoginState,
    onLogin: (username: String, password: String) -> Unit,
    onNavigateToMain: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.authToken != null) {
            onNavigateToMain.invoke()
        }

        if (state.error != null) {
            Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            val username = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome back!",
                    style = MaterialTheme.typography.h4
                )
                Spacer(Modifier.height(32.dp))
                TextField(
                    value = username.value,
                    onValueChange = { username.value = it },
                    label = { Text("Username") }
                )
                Spacer(Modifier.height(16.dp))
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = { onLogin(username.value, password.value) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Log In")
                }
            }
        }
    }
}