package com.example.smartbottle.profile.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbottle.profile.domain.Profile
import com.example.smartbottle.profile.domain.ProfileRepository
import kotlinx.coroutines.flow.flow

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

    // ✅ Dummy repo (no inheritance)
    val mockRepository = object : ProfileRepository {
        override suspend fun getProfile() = flow {
            emit(com.example.smartbottle.profile.domain.ProfileResult.Success(mockProfile))
        }

        override suspend fun updateProfile(profile: Profile): Result<Unit> {
            return Result.success(Unit)
        }
    }

    val dummyViewModel = ProfileViewModel(mockRepository)

    ProfileScreenCore(
        state = ProfileState(
            isLoading = false,
            isError = false,
            profile = mockProfile
        ),
        onAction = {},
        onNavigation = {},
        viewModel = dummyViewModel // ✅ fixed injection
    )
}
