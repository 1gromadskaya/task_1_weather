package com.example.project.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project.viewmodel.WeatherViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val weatherState by viewModel.weatherState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var city by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Введите город") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.fetchWeather(city) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поиск")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        }

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        weatherState?.let { weather ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = weather.name, style = MaterialTheme.typography.headlineMedium)

                    val iconCode = weather.weather.firstOrNull()?.icon
                    if (iconCode != null) {
                        KamelImage(
                            resource = asyncPainterResource(data = "https://openweathermap.org/img/wn/${iconCode}@2x.png"),
                            contentDescription = "",
                            modifier = Modifier.size(100.dp),
                            onLoading = { CircularProgressIndicator() },
                            onFailure = { Text("Ошибка фото", color = MaterialTheme.colorScheme.error) }
                        )
                    } else {
                        Text("Иконка недоступна", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.error)
                    }

                    Text(text = "${weather.main.temp} °C", style = MaterialTheme.typography.displayLarge)
                    Text(text = weather.weather.firstOrNull()?.description ?: "", style = MaterialTheme.typography.titleMedium)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = "Влажность: ${weather.main.humidity}%")
                        Text(text = "Ветер: ${weather.wind.speed} м/с")
                    }
                }
            }
        }
    }
}