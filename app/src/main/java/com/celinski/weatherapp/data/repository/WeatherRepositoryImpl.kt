package com.celinski.weatherapp.data.repository

import com.celinski.weatherapp.data.api.WeatherApi
import com.celinski.weatherapp.data.mappers.toWeatherInfo
import com.celinski.weatherapp.domain.repository.WeatherRepository
import com.celinski.weatherapp.domain.util.Helper
import com.celinski.weatherapp.domain.entity.WeatherMultipleModel
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private  val api: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherMultipleModel(lat: Double, long: Double): Helper<WeatherMultipleModel> {
        return try {
            Helper.Success(
                data = api.getWeatherFromApi(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Helper.Error(e.message ?: "Unsupported error occurred")
        }
    }
}