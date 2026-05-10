package tests

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import com.example.project.ui.WeatherScreen
import com.example.project.viewmodel.WeatherViewModel
import kotlin.test.Test

class WeatherUiTests {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testTextFieldExists() = runComposeUiTest {
        setContent { WeatherScreen(WeatherViewModel()) }
        onNodeWithText("Введите город").assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testSearchButtonExists() = runComposeUiTest {
        setContent { WeatherScreen(WeatherViewModel()) }
        onNodeWithText("Поиск").assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testTypingInTextField() = runComposeUiTest {
        setContent { WeatherScreen(WeatherViewModel()) }
        onNodeWithText("Введите город").performTextInput("Paris")
        onNodeWithText("Paris").assertExists()
    }
}