// File: Environment.kt
package com.example.smartbottle.water.domain

import kotlinx.serialization.Serializable

@Serializable
data class Environment(
    val outsideTemp: Double,
    val waterTemp: Double,
    val humidity: Double
)
