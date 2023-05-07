package com.celinski.weatherapp.data.mappers

import com.celinski.weatherapp.data.api.WeatherDataDto
import com.celinski.weatherapp.data.api.WeatherDto
import com.celinski.weatherapp.domain.weather.WeatherModel
import com.celinski.weatherapp.domain.weather.WeatherMultipleModel
import com.celinski.weatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherModel
)

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherModel>> {
    return timeList.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherModel(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        //API zwraca 168 pozycji - po 24 godziny na najblizsze 7 dni
        it.index / 24
    }.mapValues {
        //po podziale na mape potrzebujemy jeszcze zmapowac do listy
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherMultipleModel {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherMultipleModel(
        weatherModelPerDay = weatherDataMap,
        currentWeatherModel = currentWeatherData
    )
}