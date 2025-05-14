package com.example.smartbottle.profile.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val age: Int? = null,
    val height: Double? = null,
    val sex: String? = null,
    val user_id: String? = null,
    val weight: Double? = null
)