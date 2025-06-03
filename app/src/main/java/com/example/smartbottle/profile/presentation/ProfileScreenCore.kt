// ProfileScreenCore.kt
package com.example.smartbottle.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartbottle.profile.domain.Profile
import com.example.smartbottle.profile.presentation.components.PersonalInfoCardWithStyle
import com.example.smartbottle.profile.presentation.components.ProfileCardWithStyle
import com.example.smartbottle.profile.presentation.components.NotificationCardWithStyle

@Composable
fun ProfileScreenCore(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    onNavigation: () -> Unit,
    viewModel: ProfileViewModel
) {
    val profile = state.profile

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFF8FAFC))
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(top = 30.dp, start = 26.dp, end = 26.dp)) {
                ProfileCardWithStyle(profile)
                Spacer(modifier = Modifier.height(16.dp))
                NotificationCardWithStyle(state, onAction)
                Spacer(modifier = Modifier.height(16.dp))
                PersonalInfoCardWithStyle(profile)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
