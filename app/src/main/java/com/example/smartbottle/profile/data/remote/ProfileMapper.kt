package com.example.smartbottle.profile.data.remote

import com.example.smartbottle.profile.domain.Profile

fun ProfileDto.toProfile(): Profile {
    return Profile(
        age = age,
        height = height,
        sex = sex,
        user_id = user_id,
        weight = weight,
        totalDays = totalDays,
        longestStreak = longestStreak,
        hydration = hydration,
        alertTemperature = if (alertTemperature == null) "" else alertTemperature.toString(),
        hydrationReminder =if (hydrationReminder == null) "" else hydrationReminder.toString(),
        dndStart = if (dndStart == null) "" else dndStart.toString(),
        dndEnd = if (dndEnd == null) "" else dndEnd.toString()
    )
}


fun Profile.toDto(): ProfileDto {
    return ProfileDto(
        age = age,
        height = height,
        sex = sex,
        user_id = user_id,
        weight = weight,
        totalDays = totalDays,
        longestStreak = longestStreak,
        hydration = hydration,
        alertTemperature = alertTemperature.toDoubleOrNull(),
        hydrationReminder = hydrationReminder.toIntOrNull(),
        dndStart = dndStart.toIntOrNull(),
        dndEnd = dndEnd.toIntOrNull()
    )
}