package com.mokaz.weather.integration;

import com.mokaz.weather.core.CurrentWeather;
import com.mokaz.weather.core.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Service
class OpenMeteoCurrentWeather implements CurrentWeather {

    private static final String CURRENT_PARAM_VALUE = "temperature_2m,wind_speed_10m";
    private final RestClient restClient;

    @Override
    public Weather get(double longitude, double latitude) {
        final var response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("current", CURRENT_PARAM_VALUE)
                        .build())
                .retrieve()
                .body(OpenMeteoResponse.class);

        return response.toWeather();
    }
}
