package io.github.mikaojk.application

import io.github.mikaojk.api.registerValidateDataApi
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.mikaojk.db.Database
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.github.mikaojk.log

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
                exception<Throwable> { cause ->
                    call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Unknown error")
                    log.error("Caught exception while trying to validate some data", cause)
                    throw cause
                }
            }
        }