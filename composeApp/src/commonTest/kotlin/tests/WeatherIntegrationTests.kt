package tests

import com.example.project.network.WeatherApiClient
import com.russhwolf.settings.Settings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class WeatherIntegrationTests {
    @Test
    fun testRealNetworkCall() = runTest {
        val client = WeatherApiClient()
        val response = client.getWeather("London")
        assertNotNull(response)
    }

    @Test
    fun testSettingsSave() {
        val settings = Settings()
        settings.putString("test_key", "test_value")
        val result = settings.getStringOrNull("test_key")
        assertEquals("test_value", result)
    }

    @Test
    fun testSettingsLoadMissingKey() {
        val settings = Settings()
        val result = settings.getStringOrNull("missing_key_123")
        assertEquals(null, result)
    }
}