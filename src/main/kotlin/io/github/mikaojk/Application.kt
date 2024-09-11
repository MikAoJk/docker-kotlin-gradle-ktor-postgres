package io.github.mikaojk

import io.github.mikaojk.db.Database
import io.github.mikaojk.plugins.configureContentNegotiation
import io.github.mikaojk.plugins.configureCors
import io.github.mikaojk.plugins.configureRouting
import io.github.mikaojk.plugins.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.concurrent.TimeUnit
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val <T : Any> T.log: Logger
    get() = LoggerFactory.getLogger(this::class.java)

fun main() {
    val embeddedServer =
        embeddedServer(
            Netty,
            port = 8080,
            module = Application::module,
        )
    Runtime.getRuntime()
        .addShutdownHook(
            Thread {
                embeddedServer.stop(TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(10))
            },
        )
    embeddedServer.start(true)
}


fun Application.module() {
    val environmentVariables = EnvironmentVariables()
    val database = Database(environmentVariables)

    configureStatusPages()
    configureContentNegotiation()
    configureCors()
    configureRouting(database)
}
