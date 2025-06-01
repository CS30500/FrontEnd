// ProfileScreen.kt
package com.example.smartbottle.profile.presentation

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewmodel: ProfileViewModel = koinViewModel(),
    onNavigation: () -> Unit
) {
    ProfileScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation = onNavigation
    )
}
