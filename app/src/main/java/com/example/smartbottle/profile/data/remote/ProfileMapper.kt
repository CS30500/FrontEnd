package com.example.smartbottle.profile.data.remote

import com.example.smartbottle.profile.domain.Profile

fun ProfileDto.toProfile(): Profile {
    return Profile(
        age = age,
        height = height,
        sex = sex,
        user_id = user_id,
        weight = weight
    )
}