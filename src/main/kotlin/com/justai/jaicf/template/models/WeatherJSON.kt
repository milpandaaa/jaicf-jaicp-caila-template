package com.justai.jaicf.template.models


import com.google.gson.annotations.SerializedName

data class WeatherJSON(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<MyList>,
    @SerializedName("message")
    val message: Int
)