package io.github.mikaojk.api

import io.github.mikaojk.db.DatabaseInterface
import io.github.mikaojk.services.ValidateService
import io.github.mikaojk.services.ValidationData
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post

fun Routing.registerValidateDataApi(database: DatabaseInterface) {
    post("/v1/validate") {
        val validationData: ValidationData = call.receive()

        val validationResult = ValidateService().executeValidateData(validationData, database)
        call.respond(validationResult)
    }
}
