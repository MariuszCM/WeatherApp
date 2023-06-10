package com.celinski.weatherapp.domain.dao

import com.celinski.weatherapp.data.repository.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class WeatherModelDaoModule {
    @Provides
    fun provideWeatherModelDao(database: AppDatabase): WeatherModelDao {
        return database.weatherDao()
    }
}