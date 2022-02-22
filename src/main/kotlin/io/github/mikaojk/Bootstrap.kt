package io.github.mikaojk

import io.github.mikaojk.application.ApplicationServer
import io.github.mikaojk.application.createApplicationEngine
import io.github.mikaojk.db.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("io.github.mikaojk.Bootstrap")

fun main() {

    val database = Database()

    val applicationEngine = createApplicationEngine(database)

    val applicationServer = ApplicationServer(applicationEngine)
    applicationServer.start()

}