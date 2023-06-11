package com.celinski.weatherapp.domain.util

sealed class Helper<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Helper<T>(data)
    class Error<T>(message: String, data: T? = null) : Helper<T>(data, message)
}
