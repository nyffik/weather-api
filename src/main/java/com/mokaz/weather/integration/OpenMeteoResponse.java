package com.mokaz.weather.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mokaz.weather.core.Weather;
import com.mokaz.weather.core.WeatherSource;
import java.time.Instant;

public record OpenMeteoResponse(double latitude, double longitude, Current current) {
    public record Current(
            @JsonProperty("temperature_2m") double temperature2m,
            @JsonProperty("wind_speed_10m") double windSpeed10m) {}

    public Weather toWeather() {
        return new Weather(
                new Weather.Location(latitude, longitude),
                new Weather.Current(current().temperature2m, current().windSpeed10m()),
                WeatherSource.OPEN_METEO,
                Instant.now());
    }
}
