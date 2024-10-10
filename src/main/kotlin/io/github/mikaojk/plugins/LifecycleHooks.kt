package io.github.mikaojk.plugins

import io.github.mikaojk.logger
import io.ktor.server.application.*

fun Application.configureLifecycleHooks() {

    this.monitor.subscribe(ApplicationStarted) { logger.info("Application Started!") }
    this.monitor.subscribe(ApplicationStopped) { logger.info("Application Stoped!") }

}
