group = "io.github.MikAoJk"
version = "1.0.0-SNAPSHOT"

val ktorVersion = "2.3.6"
val junitJupiterVersion = "5.10.1"
val logbackVersion = "1.4.11"
val logstashEncoderVersion = "7.4"
val kotlinVersion = "1.9.20"
val jacksonVersion = "2.16.0"
val postgresqlVersion = "42.5.4"
val hikariCPVersion = "5.1.0"
val flywayVersion= "10.0.1"
val otjPgEmbeddedVersion = "1.0.2"
val postgresVersion = "42.6.0"
val commonsCodecVersion = "1.16.0"
val ktfmtVersion = "0.44"
val javaVersion = JavaVersion.VERSION_21

plugins {
    id("application")
    kotlin("jvm") version "1.9.20"
    id("com.diffplug.spotless") version "6.22.0"
}

application {
    mainClass.set("io.github.mikaojk.ApplicationKt")
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
    compileOnly("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    constraints {
        testImplementation("commons-codec:commons-codec:$commonsCodecVersion") {
            because("override transient from io.ktor:ktor-server-test-host")
        }
    }
    testImplementation("com.opentable.components:otj-pg-embedded:$otjPgEmbeddedVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:$ktorVersion")
}


tasks {

    compileKotlin {
        kotlinOptions.jvmTarget = javaVersion.toString()
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = javaVersion.toString()
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

}
