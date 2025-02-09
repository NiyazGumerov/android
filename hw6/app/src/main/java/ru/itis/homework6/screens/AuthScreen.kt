package ru.itis.homework6.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework6.App
import ru.itis.homework6.MainActivity
import ru.itis.homework6.R
import ru.itis.homework6.USER_ID_KEY
import ru.itis.homework6.data.crypto.hashPassword
import ru.itis.homework6.navigate.Route
import ru.itis.homework6.ui.theme.Homework6Theme

@Composable
fun AuthScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isUsernameError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val userRepository = (context as MainActivity).userRepository


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                isUsernameError = false
                errorMessage = null
            },
            label = { Text(stringResource(R.string.username)) },
            isError = isUsernameError
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordError = false
                errorMessage = null
            },
            label = { Text(stringResource(R.string.password)) },
            isError = isPasswordError,
            visualTransformation = PasswordVisualTransformation()
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick =
            {
                when {
                    username.isEmpty() -> {
                        isUsernameError = true
                        errorMessage = context.getString(R.string.username_is_empty)

                    }

                    password.isEmpty() -> {
                        isPasswordError = true
                        errorMessage = context.getString(R.string.password_is_empty)
                    }

                    else -> {
                        val coroutineScope = CoroutineScope(Dispatchers.IO)
                        coroutineScope.launch {
                            if (userRepository.getUserByUsername(username = username) == null
                                || userRepository.getUserByUsername(username = username)?.hashedPassword != hashPassword(password)
                            ) {
                                errorMessage =
                                    context.getString(R.string.incorrect_login_or_password)
                            } else {
                                val userId =
                                    userRepository.getUserByUsername(username = username)!!.id
                                (context.applicationContext as App).getPreferences().edit()?.let {
                                    it.putString(USER_ID_KEY, userId)
                                    it.commit()
                                }
                                withContext(Dispatchers.Main) {
                                    navController.navigate(Route.TRACK_LIST_ROUTE.destination)
                                }
                            }
                        }
                    }
                }
            }
        ) { Text(stringResource(id = R.string.login)) }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Route.REGISTER_ROUTE.destination) }) { Text(stringResource(id = R.string.registration)) }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    Homework6Theme {
        AuthScreen(navController = rememberNavController())
    }
}