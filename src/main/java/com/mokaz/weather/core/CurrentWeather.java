package com.mokaz.weather.core;

import org.springframework.cache.annotation.Cacheable;

public interface CurrentWeather {

    @Cacheable("weather")
    Weather get(double longitude, double latitude);
}
