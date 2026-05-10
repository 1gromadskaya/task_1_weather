package com.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.models.WeatherResponse
import com.example.project.network.WeatherApiClient
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WeatherViewModel : ViewModel() {
    private val apiClient = WeatherApiClient()
    private val settings = Settings()

    private val _weatherState = MutableStateFlow<WeatherResponse?>(null)
    val weatherState: StateFlow<WeatherResponse?> = _weatherState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchWeather(city: String) {
        val cityName = city.trim().lowercase()
        if (cityName.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = apiClient.getWeather(cityName)
                if (response != null) {
                    _weatherState.value = response
                    settings.putString("cache_$cityName", Json.encodeToString(response))
                } else {
                    loadFromCache(cityName)
                }
            } catch (e: Exception) {
                loadFromCache(cityName)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadFromCache(cityName: String) {
        val cached = settings.getStringOrNull("cache_$cityName")
        if (cached != null) {
            try {
                _weatherState.value = Json.decodeFromString(cached)
                _errorMessage.value = "Офлайн режим. Данные из кэша."
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка сети и кэш поврежден"
            }
        } else {
            _errorMessage.value = "Нет сети и данных по этому городу в кэше"
        }
    }
}