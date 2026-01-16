package com.mokaz.weather.core;

import java.time.Instant;

public record Weather(Location location, Current current, WeatherSource source, Instant retrievedAt) {
    public record Location(double latitude, double longitude) {}

    public record Current(double temperatureC, double windSpeedKmh) {}
}
