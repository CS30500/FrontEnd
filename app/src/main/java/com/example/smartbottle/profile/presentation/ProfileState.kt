package com.example.smartbottle.profile.presentation

import com.example.smartbottle.profile.domain.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
