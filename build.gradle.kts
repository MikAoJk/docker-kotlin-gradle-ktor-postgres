import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.ServiceFileTransformer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


group = "io.github.MikAoJk"
version = "1.0.0-SNAPSHOT"

val ktorVersion = "1.6.8"
val junitJupiterVersion = "5.8.2"
val logbackVersion = "1.2.11"
val logstashEncoderVersion = "7.0.1"
val kotlinVersion = "1.6.10"
val postgresqlVersion = "42.3.1"
val hikariCPVersion = "5.0.1"
val flywayVersion= "8.5.2"
val otjPgEmbeddedVersion = "1.0.0"


plugins {
    java
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")


    implementation("com.zaxxer:HikariCP:$hikariCPVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")


    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion") {
        exclude(group = "org.eclipse.jetty")
    }
    testImplementation("com.opentable.components:otj-pg-embedded:$otjPgEmbeddedVersion")
}


tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    withType<ShadowJar> {
        transform(ServiceFileTransformer::class.java) {
            setPath("META-INF/cxf")
            include("bus-extensions.txt")
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
        }
    }

}
