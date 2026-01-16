package com.mokaz.weather.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mokaz.weather.core.CurrentWeather;
import com.mokaz.weather.core.Weather;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
class WeatherController {

    private final CurrentWeather currentWeather;

    @GetMapping("weather")
    WeatherResponse getWeather(
            @RequestParam @Min(-180) @Max(180) double longitude, @RequestParam @Min(-90) @Max(90) double latitude) {

        return WeatherResponse.of(currentWeather.get(longitude, latitude));
    }

    private record WeatherResponse(
            LocationResponse location,
            CurrentResponse current,
            String source,

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
            Instant retrievedAt) {
        public static WeatherResponse of(Weather weather) {
            return new WeatherResponse(
                    LocationResponse.of(weather.location()),
                    CurrentResponse.of(weather.current()),
                    weather.source().getValue(),
                    weather.retrievedAt());
        }

        private record LocationResponse(double lat, double lon) {
            private static LocationResponse of(Weather.Location location) {
                return new LocationResponse(location.latitude(), location.longitude());
            }
        }

        private record CurrentResponse(double temperatureC, double windSpeedKmh) {
            private static CurrentResponse of(Weather.Current current) {
                return new CurrentResponse(current.temperatureC(), current.windSpeedKmh());
            }
        }
    }
}
