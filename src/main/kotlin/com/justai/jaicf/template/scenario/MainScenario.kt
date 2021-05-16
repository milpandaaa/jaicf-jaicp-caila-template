package com.justai.jaicf.template.scenario

import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.exceptions.ActionException
import com.justai.jaicf.reactions.Reactions
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.template.weather.HandlerJSON
import com.justai.jaicf.template.weather.OpenWeatherMap

var city = String()
val mainScenario = Scenario {

    state("start") {
        activators {
            regex("/start")
        }
        action {
            reactions.run {
                say("Hello! Write city name")
            }
        }
    }

    state("main") {
        activators {
            catchAll()
        }

        action {
            city = request.input

            if (city.isEmpty()) {
                reactions.run {
                    say("Write city name again")
                }
            }
            else{
                print(city)
                reactions.run {
                    say("Choose a day")
                    buttons("Today" to "/today", "Tomorrow" to "/tomorrow",
                        "Day after tomorrow" to "/dayAfterTomorrow")
                }
            }
        }
    }


    state("today") {
        activators {
            intent("Today")
        }

        action(actionWeather(0))
    }

    state("tomorrow") {
        activators {
            intent("Tomorrow")
        }

        action(actionWeather(1))
    }

    state("dayAfterTomorrow") {
        activators {
            intent("Day after tomorrow")
        }

        action(actionWeather(2))
    }

    state("bye") {
        activators {
            intent("Bye")
        }

        action {
            reactions.sayRandom(
                "See you soon!",
                "Bye-bye!"
            )
        }
    }

}

private fun actionWeather(day: Int): ActionContext<ActivatorContext, BotRequest, Reactions>.() -> Unit =
    {
        if (city.isEmpty())
            reactions.say("Write city name again")
        else {
            try{
                val answer = HandlerJSON.onSuccess(
                    OpenWeatherMap.askOpenWeatherMap(day+1, city))
                if (answer != null && answer.cod == "200") {
                    reactions.say("Weather in $city")
                    reactions.say("temp: ${answer.list[day].main.temp}")
                    reactions.say("feels_like: ${answer.list[day].main.feelsLike}")
                    reactions.say("${answer.list[day].weather[0].main}( ${answer.list[day].weather[0].description})")
                    reactions.say("Write city name again or select \"Bye\" to end the dialog")
                    reactions.buttons("Bye" to "/bye")
                }
                else
                    throw Exception("Not Found City")
            }
            catch (e : Exception){
                reactions.say("Write city name again")
            }

        }
    }