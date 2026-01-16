package contracts.weather

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return 400 when latitude is invalid with Problem API body"
    request {
        method GET()
        url("/weather") {
            queryParameters {
                parameter("latitude", "200")
                parameter("longitude", "13.41")
            }
        }
    }
    response {
        status BAD_REQUEST()
        headers {
            contentType("application/problem+json")
        }
        body([ 
            detail: "Validation failure",
            instance: "/weather",
            status: 400,
            title: "Bad Request"
        ])
    }
}

