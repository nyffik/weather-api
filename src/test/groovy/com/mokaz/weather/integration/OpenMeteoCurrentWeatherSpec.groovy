package com.mokaz.weather.integration

import com.mokaz.weather.core.WeatherSource
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestClient
import spock.lang.Specification

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

class OpenMeteoCurrentWeatherSpec extends Specification {

    OpenMeteoCurrentWeather client
    MockRestServiceServer server

    def setup() {
        def builder = RestClient.builder()
                .baseUrl("https://api.open-meteo.com/v1/forecast")
        
        server = MockRestServiceServer.bindTo(builder).build()
        
        def restClient = builder.build()
        
        client = new OpenMeteoCurrentWeather(restClient)
    }

    def "should fetch current weather from OpenMeteo API"() {
        given:
        def lat = 52.52d
        def lon = 13.41d
        def jsonResponse = """
            {
                "latitude": 52.52,
                "longitude": 13.41,
                "current": {
                    "temperature_2m": 20.5,
                    "wind_speed_10m": 12.0
                }
            }
        """

        // Expectation with potential URL encoding for the comma
        def expectedUrl = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m"
        
        server.expect(requestTo(expectedUrl))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON))

        when:
        def result = client.get(lon, lat)

        then:
        result.location().latitude() == lat
        result.location().longitude() == lon
        result.current().temperatureC() == 20.5
        result.current().windSpeedKmh() == 12.0
        result.source() == WeatherSource.OPEN_METEO
    }
}
