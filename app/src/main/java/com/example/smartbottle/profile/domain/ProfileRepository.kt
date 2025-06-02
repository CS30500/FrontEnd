package com.example.smartbottle.profile.domain

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<ProfileResult<Profile>>
    suspend fun updateProfile(profile: Profile): ProfileResult<Unit>
}