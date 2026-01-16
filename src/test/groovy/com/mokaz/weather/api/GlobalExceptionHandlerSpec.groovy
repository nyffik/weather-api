package com.mokaz.weather.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import org.spockframework.spring.SpringBean

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.mokaz.weather.core.CurrentWeather

@WebMvcTest(controllers = [WeatherController])
@Import(GlobalExceptionHandler)
class GlobalExceptionHandlerSpec extends Specification {

    @Autowired
    MockMvc mvc

    @SpringBean
    CurrentWeather currentWeather = Mock()

    def "should return ProblemDetail for unhandled exception"() {
        given:
        currentWeather.get(_, _) >> { throw new RuntimeException("Something went wrong") }

        expect:
        mvc.perform(get("/weather")
                .param("latitude", "52.52")
                .param("longitude", "13.41"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath('$.title').value("Internal Server Error"))
                .andExpect(jsonPath('$.detail').value("Something went wrong"))
                .andExpect(jsonPath('$.status').value(500))
    }
}
