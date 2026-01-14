##  		Real Temperature Proxy API

### Goal

Build a small REST API that fetches current temperature from Open-Meteo and returns a normalized response. Propose endpoint structure.

Integration

* Call Open-Meteo Forecast API:  
  * Base: `https://api.open-meteo.com/v1/forecast`  
  * Use the `current` parameter to fetch current conditions (Open-Meteo supports `current` variables). ([Open Meteo](https://open-meteo.com/en/docs))

Example upstream call (this returns real current values):

```shell
curl "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current=temperature_2m,wind_speed_10m"
```

(Open-Meteo documents `/v1/forecast` and the `current` parameter for current conditions.) ([Open Meteo](https://open-meteo.com/en/docs))

Your API response shape

```json
{
 "location": { "lat": 52.52, "lon": 13.41 },
  "current": {
    "temperatureC": 1.2,
    "windSpeedKmh": 9.7
  },
  "source": "open-meteo",
  "retrievedAt": "2026-01-11T10:12:54Z"
}
```

### Explicit requirements

* Timeout to the upstream (e.g., 1s)  
* Cache by `(lat, lon)` for 60 seconds  
* Besides that project should contain all important bits you can think the production code should have  
* Bonus points  
  * k8s containerization  
  * health checks, resource limits, configuration (timeouts, cache TTLs)scaling

The implementation should be done in Java or Kotlin. For any unknowns(libraries, etc), please  show your seniority and raise your proposals. Once completed, please publish the solution on GitHub and share the repository link via email at albert@tequipy.com. There is no strict deadline for this task; however, earlier completion is appreciated. 