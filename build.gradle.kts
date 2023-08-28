group = "io.github.MikAoJk"
version = "1.0.0-SNAPSHOT"

val jvmTargetVersion = "17"

val ktorVersion = "2.3.3"
val junitJupiterVersion = "5.10.0"
val logbackVersion = "1.4.11"
val logstashEncoderVersion = "7.4"
val kotlinVersion = "1.9.0"
val jacksonVersion = "2.15.2"
val postgresqlVersion = "42.5.4"
val hikariCPVersion = "5.0.1"
val flywayVersion= "9.21.2"
val otjPgEmbeddedVersion = "1.0.1"
val postgresVersion = "42.6.0"
val commonsCodecVersion = "1.16.0"
val ktfmtVersion = "0.44"

plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.diffplug.spotless") version "6.20.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    implementation("com.zaxxer:HikariCP:$hikariCPVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    //Provides transitive vulnerable dependency maven:commons-codec:commons-codec:1.11
    testImplementation("commons-codec:commons-codec:$commonsCodecVersion")
    testImplementation("com.opentable.components:otj-pg-embedded:$otjPgEmbeddedVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:$ktorVersion")
}


tasks {
    shadowJar {
        archiveBaseName.set("app")
        archiveClassifier.set("")
        isZip64 = true
        manifest {
            attributes(
                mapOf(
                    "Main-Class" to "io.github.mikaojk.ApplicationKt",
                ),
            )
        }
    }

    test {
        useJUnitPlatform {}
        testLogging {
            showStandardStreams = true
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
    spotless {
        kotlin { ktfmt(ktfmtVersion).kotlinlangStyle() }
        check {
            dependsOn("spotlessApply")
        }
    }


    build {
        dependsOn(shadowJar)
    }

}
