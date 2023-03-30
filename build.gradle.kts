import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


group = "io.github.MikAoJk"
version = "1.0.0-SNAPSHOT"

val jvmTargetVersion = "17"

val ktorVersion = "2.2.4"
val junitJupiterVersion = "5.9.2"
val logbackVersion = "1.4.6"
val logstashEncoderVersion = "7.3"
val kotlinVersion = "1.8.20"
val jacksonVersion = "2.14.2"
val postgresqlVersion = "42.5.4"
val hikariCPVersion = "5.0.1"
val flywayVersion= "9.16.1"
val otjPgEmbeddedVersion = "1.0.1"
val postgresVersion = "42.6.0"


plugins {
    java
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")

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
    testImplementation("com.opentable.components:otj-pg-embedded:$otjPgEmbeddedVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:$ktorVersion")
}


tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTargetVersion
    }

    named<KotlinCompile>("compileTestKotlin") {
        kotlinOptions.jvmTarget = jvmTargetVersion
    }

    withType<ShadowJar> {
        archiveBaseName.set(project.name)
        mergeServiceFiles()
        manifest.attributes["Main-Class"] = "io.github.mikaojk.BootstrapKt"
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
    withType<Wrapper> {
        gradleVersion = "8.0.1"
    }

    build {
        dependsOn(shadowJar)
    }

}
