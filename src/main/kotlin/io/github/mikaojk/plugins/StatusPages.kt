package io.github.mikaojk.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.github.mikaojk.log


fun Application.configureStatusPages() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            log.error("Caught exception ${cause.message}")
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Unknown error")
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Page Not Found", status = status)
        }
    }
}
