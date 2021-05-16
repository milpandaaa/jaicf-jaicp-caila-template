package com.justai.jaicf.template.weather

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL
import java.net.URLEncoder


class OpenWeatherMap {

    companion object {

        fun askOpenWeatherMap(day: Int, city: String): String? {

            var apiKey = "fcbac5e87f4fce814193996efddd4fa3"
            val url =
                "http://api.openweathermap.org/data/2.5/forecast?q=$city&cnt=$day&units=metric&appid=$apiKey"
            return executePost(url)
        }

        @Throws(Exception::class)
        private fun executePost(url: String?): String? {
            val yahoo = URL(url)
            val yc = yahoo.openConnection()
            val `in` = BufferedReader(InputStreamReader(yc.getInputStream()))
            var inputLine: String?
            val outLine = StringBuilder()
            while (`in`.readLine().also { inputLine = it } != null) outLine.append(inputLine)
            `in`.close()
            return outLine.toString()
        }

    }

}