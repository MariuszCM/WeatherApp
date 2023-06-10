package com.celinski.weatherapp.data.repository

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromString(value: String?): LocalDateTime? {
        return value?.let {
            return LocalDateTime.parse(it, formatter)
        }
    }

    @TypeConverter
    fun toString(value: LocalDateTime?): String? {
        return value?.let {
            return it.format(formatter)
        }
    }
}