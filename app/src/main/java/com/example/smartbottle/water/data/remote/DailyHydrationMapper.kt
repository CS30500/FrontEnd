package com.example.smartbottle.water.data.remote

import com.example.smartbottle.water.domain.DailyHydration

fun DailyHydrationDto.toDailyHydration(): DailyHydration {
    return DailyHydration(
        date = date,
        target_ml = target_ml,
        total_intake_ml = total_intake_ml
    )

}