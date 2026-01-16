package com.mokaz.weather.integration

import com.mokaz.weather.core.WeatherSource
import spock.lang.Specification

class OpenMeteoResponseSpec extends Specification {

    def "should map OpenMeteoResponse to Weather domain object"() {
        given:
        def lat = 12.34d
        def lon = 56.78d
        def temp = 25.5d
        def wind = 10.2d
        def response = new OpenMeteoResponse(lat, lon, new OpenMeteoResponse.Current(temp, wind))

        when:
        def weather = response.toWeather()

        then:
        weather.location().latitude() == lat
        weather.location().longitude() == lon
        weather.current().temperatureC() == temp
        weather.current().windSpeedKmh() == wind
        weather.source() == WeatherSource.OPEN_METEO
        weather.retrievedAt() != null
    }
}
