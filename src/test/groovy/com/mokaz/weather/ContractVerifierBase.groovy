package com.mokaz.weather

import com.mokaz.weather.api.GlobalExceptionHandler
import com.mokaz.weather.api.WeatherController
import com.mokaz.weather.core.CurrentWeather
import com.mokaz.weather.core.Weather
import com.mokaz.weather.core.WeatherSource
import io.restassured.module.mockmvc.RestAssuredMockMvc
import spock.lang.Specification

import java.time.Instant

class ContractVerifierBase extends Specification {

    def setup() {
        def currentWeather = Mock(CurrentWeather)
        def weather = new Weather(
                new Weather.Location(52.52d, 13.41d),
                new Weather.Current(15.0d, 10.0d),
                WeatherSource.OPEN_METEO,
                Instant.parse("2026-01-16T12:00:00Z")
        )
        currentWeather.get(13.41d, 52.52d) >> weather

        RestAssuredMockMvc.standaloneSetup(new WeatherController(currentWeather), new GlobalExceptionHandler())
    }
}
