package io.github.mikaojk.application

import io.github.mikaojk.api.registerValidateDataApi
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.mikaojk.db.Database
import io.ktor.http.HttpStatusCode
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.github.mikaojk.log
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText

fun createApplicationEngine(database: Database): ApplicationEngine =
    embeddedServer(Netty, 8080) {
        routing {
            registerValidateDataApi(database)
        }
        install(ContentNegotiation) {
            jackson {
                registerKotlinModule()
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
