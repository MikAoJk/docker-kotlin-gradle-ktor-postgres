package io.github.mikaojk.application

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.mikaojk.api.registerValidateDataApi
import io.github.mikaojk.db.Database
import io.ktor.http.HttpStatusCode
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.cio.CIO
import io.github.mikaojk.log
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText

fun createApplicationEngine(database: Database): ApplicationEngine =
    embeddedServer(CIO, 8080) {
        routing {
            registerValidateDataApi(database)
        }
        install(ContentNegotiation) {
            jackson {
                registerKotlinModule()
                registerModule(JavaTimeModule())
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                setSerializationInclusion(JsonInclude.Include.NON_NULL)
            }
        }
        install(StatusPages) {
            exception<Throwable> { call, cause ->
                call.respondText(
                    text = "500: $cause.message ?: Unknown error",
                    status = HttpStatusCode.InternalServerError
                )
                log.error("Caught exception while trying to validate some data", cause)
                throw cause
            }
        }
    }
