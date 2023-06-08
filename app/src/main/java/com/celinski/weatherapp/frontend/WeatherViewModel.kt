package com.celinski.weatherapp.frontend

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celinski.weatherapp.domain.gps.GpsTracker
import com.celinski.weatherapp.domain.repository.WeatherRepository
import com.celinski.weatherapp.domain.util.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val gpsTracker: GpsTracker,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            gpsTracker.getCurrentLocation()?.let { location ->
                when (val result = repository.getWeatherMultipleModel(location.latitude, location.longitude)) {
                    is Helper.Success -> {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        state = state.copy(weatherMultipleModel = result.data, isLoading = false, error = null,  city = geocoder.getFromLocation(location.latitude, location.longitude, 1).get(0).locality)
                    }

                    is Helper.Error -> {
                        state = state.copy(weatherMultipleModel = null, isLoading = false, error = result.message)
                    }
                }
            } ?: kotlin.run {
                //TODO dodac komunikat
                state = state.copy(isLoading = false, error = "")
            }
        }
    }
}