package com.example.smartbottle.auth.presentation.signup

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
fun RegisterScreen(
    viewmodel : RegisterViewModel = koinViewModel(),
    onNavigation : () -> Unit
){
    RegisterScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation
    )
}

@Composable
private fun RegisterScreenCore(
    state : RegisterState,
    onAction : (RegisterAction) -> Unit,
    onNavigation : () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Register Screen")

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(RegisterAction.ChangeEmail(it)) },
            label = { Text(text = "Email") })

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(RegisterAction.ChangeEmail(it)) },
            label = { Text(text = "Password") })

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(RegisterAction.ChangeEmail(it)) },
            label = { Text(text = "Check Password") })

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onAction(RegisterAction.Register)
                onNavigation()
            }) {
            Text(text = "Register")
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRegisterScreenCore(){

    SmartBottleTheme {
        RegisterScreenCore(state = RegisterState(), onAction = {}, onNavigation = {})
    }
}