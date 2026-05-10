package com.example.project.network

import com.example.project.models.WeatherResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class WeatherApiClient {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getWeather(city: String): WeatherResponse? {
        return try {
            val apiKey = "bd5e378503939ddaee76f12ad7a97608"
            val url = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric&lang=ru"

            httpClient.get(url).body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}