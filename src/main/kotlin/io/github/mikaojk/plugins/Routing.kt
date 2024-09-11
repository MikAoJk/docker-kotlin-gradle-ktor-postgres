package io.github.mikaojk.plugins


import io.github.mikaojk.api.registerValidateDataApi
import io.github.mikaojk.db.Database
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*


fun Application.configureRouting(database: Database) {

    routing {
        registerValidateDataApi(database)
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
    }
}
