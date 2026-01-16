package contracts.weather

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return weather for given location"
    request {
        method GET()
        url("/weather") {
            queryParameters {
                parameter("latitude", "52.52")
                parameter("longitude", "13.41")
            }
        }
    }
    response {
        status OK()
        body([
            location: [
                lat: 52.52,
                lon: 13.41
            ],
            current: [
                temperatureC: 15.0,
                windSpeedKmh: 10.0
            ],
            source: "open-meteo",
            retrievedAt: "2026-01-16T12:00:00Z"
        ])
        headers {
            contentType(applicationJson())
        }
    }
}
