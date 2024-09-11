package io.github.mikaojk.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.mikaojk.TestDB
import io.github.mikaojk.dropData
import io.github.mikaojk.services.ValidationData
import io.github.mikaojk.services.ValidationResult
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationClient
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders.Accept as AcceptHeader
import io.ktor.http.HttpHeaders.ContentType as ContentTypeHeader
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationServer
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ValidateDataApiTest {

    private val objectMapper: ObjectMapper =
        ObjectMapper()
            .registerKotlinModule()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    private val database = TestDB()

    @Test
    internal fun `Returns OK when input it DATA`() {
        testApplication {
            application {
                routing {
                    registerValidateDataApi(database)
                }

                install(ContentNegotiationServer) {
                    jackson {
                        registerKotlinModule()
                        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    }
                }
            }

            val validationData = ValidationData("DATA")

            val response = client.post("/v1/validate") {
                header(ContentTypeHeader, ContentType.Application.Json)
                header(AcceptHeader, ContentType.Application.Json)
                setBody(objectMapper.writeValueAsString(validationData))
            }

            assertEquals(response.status, HttpStatusCode.OK)
            assertEquals(
                response.bodyAsText(),
                objectMapper.writeValueAsString(ValidationResult("OK")),
            )

        }

    }

    @Test
    internal fun `Returns WRONG when input is not DATA`() {
        testApplication {
            application {
                routing { registerValidateDataApi(database) }
                install(ContentNegotiationServer) {
                    jackson {
                        registerKotlinModule()
                        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    }
                }
            }

            val validationData = ValidationData("DATA1")

            val client = createClient {
                install(ContentNegotiationClient) {
                    jackson {
                        registerKotlinModule()
                        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    }
                }
            }

            val response =
                client
                    .preparePost("/v1/validate") {
                        accept(ContentType.Application.Json)
                        contentType(ContentType.Application.Json)
                        setBody(objectMapper.writeValueAsString(validationData))
                    }
                    .execute()

            assertEquals(response.status, HttpStatusCode.OK)
            assertEquals(response.body<ValidationResult>(), ValidationResult("INVALID"))
        }
    }

    @AfterAll
    internal fun afterAll() {
        database.connection.dropData()
        database.stop()
    }
}
