package tests

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import com.example.project.ui.WeatherScreen
import com.example.project.viewmodel.WeatherViewModel
import com.russhwolf.settings.MapSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test

class WeatherUiTests {

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalTestApi::class)
    @Ignore // Отключаем тест, так как графике нужен реальный эмулятор
    @Test
    fun testTextFieldExists() = runComposeUiTest {
        setContent { WeatherScreen(WeatherViewModel(MapSettings())) }
        onNodeWithText("Введите город").assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Ignore // Отключаем тест, так как графике нужен реальный эмулятор
    @Test
    fun testSearchButtonExists() = runComposeUiTest {
        setContent { WeatherScreen(WeatherViewModel(MapSettings())) }
        onNodeWithText("Поиск").assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Ignore // Отключаем тест, так как графике нужен реальный эмулятор
    @Test
    fun testTypingInTextField() = runComposeUiTest {
        setContent { WeatherScreen(WeatherViewModel(MapSettings())) }
        onNodeWithText("Введите город").performTextInput("Paris")
        onNodeWithText("Paris").assertExists()
    }
}