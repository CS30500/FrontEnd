package com.example.smartbottle.auth.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbottle.auth.domain.AuthResult
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    viewmodel : RegisterViewModel = koinViewModel(),
    onNavigation : () -> Unit
){
    val context = LocalContext.current
    LaunchedEffect(viewmodel, context){
        viewmodel.authResults.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    Toast.makeText(context, "register success", Toast.LENGTH_LONG).show()
                    onNavigation()
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, "Not authorized", Toast.LENGTH_LONG).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context, "Unknown Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

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
            value = state.password,
            onValueChange = { onAction(RegisterAction.ChangePassword(it)) },
            label = { Text(text = "Password") })

        OutlinedTextField(
            value = state.checkPassword,
            onValueChange = { onAction(RegisterAction.ChangeCheckPassword(it)) },
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