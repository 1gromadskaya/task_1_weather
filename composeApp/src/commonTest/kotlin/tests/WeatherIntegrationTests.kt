package tests

import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherIntegrationTests {

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSettingsSave() {
        val settings = MapSettings()
        settings.putString("cache_london", "test_data")
        assertEquals("test_data", settings.getStringOrNull("cache_london"))
    }

    @Test
    fun testSettingsLoadMissingKey() {
        val settings = MapSettings()
        assertEquals(null, settings.getStringOrNull("missing_key"))
    }
}