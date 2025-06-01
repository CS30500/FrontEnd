// CardWithStyle.kt
package com.example.smartbottle.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartbottle.core.presentation.ui.theme.Gray2
import com.example.smartbottle.profile.domain.Profile

@Composable
fun ProfileCardWithStyle(profile: Profile?) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Gray2
            )
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ){
        ProfileCard(profile)
    }
}

@Composable
fun NotificationCardWithStyle(profile: Profile?) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Gray2
            )
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        NotificationCard(profile)
    }
}

@Composable
fun PersonalInfoCardWithStyle(profile: Profile?) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Gray2
            )
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        PersonalInfoCard(profile)
    }
}