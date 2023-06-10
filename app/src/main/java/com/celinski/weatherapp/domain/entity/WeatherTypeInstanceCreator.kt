package com.celinski.weatherapp.domain.entity

import com.google.gson.InstanceCreator
import java.lang.reflect.Type

class WeatherTypeInstanceCreator : InstanceCreator<WeatherType> {
    override fun createInstance(type: Type?): WeatherType {
        return WeatherType.ClearSky // Domyślny typ, który zostanie zwrócony
    }
}