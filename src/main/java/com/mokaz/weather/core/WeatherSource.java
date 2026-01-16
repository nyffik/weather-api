package com.mokaz.weather.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WeatherSource {
    OPEN_METEO("open-meteo");

    @Getter
    private final String value;
}
