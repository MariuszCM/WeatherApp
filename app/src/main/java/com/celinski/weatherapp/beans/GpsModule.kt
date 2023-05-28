package com.celinski.weatherapp.beans

import com.celinski.weatherapp.data.gps.GpsTrackerImpl
import com.celinski.weatherapp.domain.gps.GpsTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class GpsModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(gpsTrackerImpl: GpsTrackerImpl): GpsTracker
}