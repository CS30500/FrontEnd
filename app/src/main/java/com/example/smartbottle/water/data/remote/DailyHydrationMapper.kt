package com.example.smartbottle.water.data.remote

import com.example.smartbottle.water.domain.DailyHydration
import com.example.smartbottle.water.domain.Environment


fun DailyHydrationDto.toDailyHydration(): DailyHydration {
    val env = if (outsideTemperature != null && waterTemperature != null && humidity != null) {
        Environment(
            outsideTemp = outsideTemperature,
            waterTemp = waterTemperature,
            humidity = humidity
        )
    } else null

    return DailyHydration(
        date = date,
        target_ml = target_ml,
        total_intake_ml = total_intake_ml,
        environment = env
    )
}
