package com.celinski.weatherapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    //asynchroniczna funkcja do pobierania pogody z API przy uzyciu dlosci i szerokosci geograficznej
    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherFromApi(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}