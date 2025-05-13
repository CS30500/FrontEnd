package com.example.smartbottle.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewmodel : LoginViewModel = koinViewModel(),
    onNavigation : () -> Unit,
    onRegister : () -> Unit,
){
    LoginScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation,
        onRegister
    )
}

@Composable
private fun LoginScreenCore(
    state : LoginState,
    onAction : (LoginAction) -> Unit,
    onNavigation : () -> Unit,
    onRegister : () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Login Screen")

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(LoginAction.ChangeEmail(it)) },
            label = { Text(text = "Email") })

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(LoginAction.ChangePassword(it)) },
            label = { Text(text = "Password") }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
            onAction(LoginAction.Login)
            onNavigation()
        }) {
            Text(text = "Login")
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onRegister()
            }) {
            Text(text = "Register")
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreenCore(){

    SmartBottleTheme {
        LoginScreenCore(state = LoginState(), onAction = {}, onNavigation = {}, onRegister = {})
    }
}
