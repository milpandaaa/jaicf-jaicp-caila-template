package com.justai.jaicf.template.weather

import com.google.gson.Gson
import com.justai.jaicf.template.models.WeatherJSON

class HandlerJSON {

    companion object {
        fun onSuccess(json: String?): WeatherJSON? {
            try {
                val gson = Gson()
                return gson.fromJson(json, WeatherJSON::class.java)
            } catch (e: Exception) {
                throw RuntimeException("Conversion error json", e)
            }
        }
    }

}