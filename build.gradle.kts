import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "io.github.MikAoJk"
version = "1.0.0"

val ktorVersion = "2.3.12"
val junitJupiterVersion = "5.11.0"
val logbackVersion = "1.5.8"
val logstashEncoderVersion = "8.0"
val kotlinVersion = "2.0.20"
val jacksonVersion = "2.17.2"
val postgresqlVersion = "42.5.4"
val hikariCPVersion = "5.1.0"
val flywayVersion = "10.17.2"
val otjPgEmbeddedVersion = "1.1.0"
val postgresVersion = "42.7.4"
val commonsCodecVersion = "1.17.1"
val commonsCompressVersion = "1.27.1"
val ktfmtVersion = "0.44"
val javaVersion = JvmTarget.JVM_21

plugins {
    id("application")
    kotlin("jvm") version "2.0.20"
    id("com.diffplug.spotless") version "6.25.0"
    id("com.gradleup.shadow") version "8.3.0"
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
    constraints {
        testImplementation("org.apache.commons:commons-compress:$commonsCompressVersion") {
            because("override transient from com.opentable.components:otj-pg-embedded")
        }
    }

    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:$ktorVersion")
}


kotlin {
    compilerOptions {
        jvmTarget.set(javaVersion)
    }
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

}
