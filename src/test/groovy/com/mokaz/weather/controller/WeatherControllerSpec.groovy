package com.mokaz.weather.controller

import com.mokaz.weather.api.WeatherController
import com.mokaz.weather.core.CurrentWeather
import com.mokaz.weather.core.Weather
import com.mokaz.weather.core.WeatherSource
import org.springframework.beans.factory.annotation.Autowired
import org.spockframework.spring.SpringBean
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.Instant

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(WeatherController)
@AutoConfigureMockMvc
class WeatherControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    CurrentWeather currentWeather = Mock()

    def "should return weather data"() {
        given:
        def lat = 52.52d
        def lon = 13.41d
        def response = new Weather(
                new Weather.Location(lat, lon),
                new Weather.Current(15.0, 10.0),
                WeatherSource.OPEN_METEO,
                Instant.now()
        )

        1 * currentWeather.get(lon, lat) >> response

        expect:
        mvc.perform(get("/weather")
                .param("latitude", lat.toString())
                .param("longitude", lon.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.location.lat').value(lat))
                .andExpect(jsonPath('$.location.lon').value(lon))
                .andExpect(jsonPath('$.current.temperatureC').value(15.0))
    }
}
