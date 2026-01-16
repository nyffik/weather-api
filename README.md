# Weather API

A Spring Boot application that provides weather information. This application integrates with Open-Meteo to fetch current weather data based on geographic coordinates.

## Prerequisites

- Java 25
- Gradle (provided via wrapper)

## Building the Application

To build the project, run the following command in the root directory:

```bash
./gradlew build
```

## Running the Application

To start the application, use the Gradle `bootRun` task:

```bash
./gradlew bootRun
```

The application will start on port `8080` (default).

## Code Formatting

This project uses Spotless with Palantir Java Format to enforce code style.

To check for formatting issues:
```bash
./gradlew spotlessCheck
```

To automatically format the code:
```bash
./gradlew spotlessApply
```

## API Documentation

The application uses OpenAPI (Swagger) for API documentation.

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## API Usage

### Get Weather

Retrieves the current weather for a specific location.

**Endpoint:** `GET /weather`

**Parameters:**

| Name        | Type   | Required | Description                                      |
|-------------|--------|----------|--------------------------------------------------|
| `latitude`  | double | Yes      | Latitude of the location (-90 to 90)             |
| `longitude` | double | Yes      | Longitude of the location (-180 to 180)          |

**Example Request:**

```bash
curl "http://localhost:8080/weather?latitude=52.52&longitude=13.41"
```

**Example Response:**

```json
{
  "location": {
    "lat": 52.52,
    "lon": 13.41
  },
  "current": {
    "temperatureC": 20.5,
    "windSpeedKmh": 15.0
  },
  "source": "open-meteo",
  "retrievedAt": "2023-10-27T10:00:00Z"
}
```

## Docker Support

You can build a Docker image using Cloud Native Buildpacks.

To build the image:
```bash
./gradlew bootBuildImage
```

The image will be named `mokaz/weather-api:latest`.

Run the container:
```bash
docker run -p 8080:8080 mokaz/weather-api:latest
```

## Actuator Endpoints

The following actuator endpoints are exposed:
- `health`: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
- `info`: [http://localhost:8080/actuator/info](http://localhost:8080/actuator/info)
- `metrics`: [http://localhost:8080/actuator/metrics](http://localhost:8080/actuator/metrics)
