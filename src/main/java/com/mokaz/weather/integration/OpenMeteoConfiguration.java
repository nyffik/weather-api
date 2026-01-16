package com.mokaz.weather.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
class OpenMeteoConfiguration {

    @Bean
    RestClient weatherRestClient(
            RestClient.Builder builder,
            @Value("${weather.open-meteo.url}") String apiUrl,
            @Value("${weather.open-meteo.timeout-ms}") int timeoutMs) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeoutMs);
        factory.setReadTimeout(timeoutMs);

        return builder.requestFactory(factory).baseUrl(apiUrl).build();
    }
}
