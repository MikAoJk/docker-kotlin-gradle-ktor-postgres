package io.github.mikaojk.api

import io.github.mikaojk.db.DatabaseInterface
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.github.mikaojk.services.ValidateService
import io.github.mikaojk.services.ValidationData

fun Routing.registerValidateDataApi(database: DatabaseInterface) {
    post("/v1/validate") {

        val validationData: ValidationData = call.receive()

        val validationResult = ValidateService().executeValidateData(validationData, database)
        call.respond(validationResult)
    }
}