package com.example.smartbottle.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbottle.profile.domain.Profile

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val mockProfile = Profile(
        user_id = "Username1234",
        age = 30,
        sex = "Female",
        weight = 56.2,
        height = 165.7,
        totalDays = 264,
        longestStreak = 32,
        hydration = 94,
        alertTemperature = 24.0,
        hydrationReminder = 150,
        dndStart = 100,
        dndEnd = 900
    )

    ProfileScreenCore(
        state = ProfileState(
            isLoading = false,
            isError = false,
            profile = mockProfile
        ),
        onAction = {},
        onNavigation = {}
    )
}
