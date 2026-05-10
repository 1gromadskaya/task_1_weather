package tests

import com.example.project.models.WeatherResponse
import com.example.project.viewmodel.WeatherViewModel
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherUnitTests {
    @Test
    fun testJsonParsing() {
        val jsonString = """{"name":"Minsk","weather":[{"description":"clear","icon":"01d"}],"main":{"temp":15.0,"humidity":50},"wind":{"speed":5.0}}"""
        val jsonParser = Json { ignoreUnknownKeys = true }
        val response = jsonParser.decodeFromString<WeatherResponse>(jsonString)

        assertEquals("Minsk", response.name)
        assertEquals(15.0, response.main.temp)
        assertEquals(50, response.main.humidity)
        assertEquals(5.0, response.wind.speed)
    }

    @Test
    fun testViewModelInitialState() {
        val viewModel = WeatherViewModel()
        assertEquals(null, viewModel.weatherState.value)
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun testEmptyCitySearch() {
        val viewModel = WeatherViewModel()
        viewModel.fetchWeather("")
        assertEquals(null, viewModel.weatherState.value)
    }
}