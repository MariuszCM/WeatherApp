package com.celinski.weatherapp.data.repository

import com.celinski.weatherapp.data.api.WeatherApi
import com.celinski.weatherapp.data.mappers.toWeatherInfo
import com.celinski.weatherapp.domain.repository.WeatherRepository
import com.celinski.weatherapp.domain.util.Resource
import com.celinski.weatherapp.domain.weather.WeatherMultipleModel
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private  val api: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherMultipleModel(lat: Double, long: Double): Resource<WeatherMultipleModel> {
        return try {
            Resource.Success(
                data = api.getWeatherFromApi(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unsupported error occurred")
        }
    }
}