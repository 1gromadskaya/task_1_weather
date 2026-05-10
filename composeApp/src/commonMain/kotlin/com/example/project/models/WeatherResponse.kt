package com.example.project.models

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val main: MainWeather,
    val weather: List<WeatherDescription>,
    val wind: Wind,
    val name: String
)

@Serializable
data class MainWeather(
    val temp: Double,
    val humidity: Int
)

@Serializable
data class WeatherDescription(
    val description: String,
    val icon: String
)

@Serializable
data class Wind(
    val speed: Double
)