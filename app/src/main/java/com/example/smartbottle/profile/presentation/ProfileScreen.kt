package com.example.smartbottle.profile.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewmodel : ProfileViewModel = koinViewModel(),
    onNavigation : () -> Unit
){
    ProfileScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation
    )
}

@Composable
private fun ProfileScreenCore(
    state : ProfileState,
    onAction : (ProfileAction) -> Unit,
    onNavigation : () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(state.isLoading){
            Text("로딩중...")
        } else if(state.isError){
            Text("에러가 발생했습니다.")
        }else{
            state.profile?.user_id?.let { Text(it) }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview(){
    SmartBottleTheme {
        ProfileScreenCore(
            state = ProfileState(),
            onAction = {},
            onNavigation = {}
        )
    }
}