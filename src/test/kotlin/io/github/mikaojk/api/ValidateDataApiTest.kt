package io.github.mikaojk.api

import io.github.mikaojk.TestDB
import io.github.mikaojk.dropData
import io.github.mikaojk.services.ValidationData
import io.github.mikaojk.services.ValidationResult
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders.Accept as AcceptHeader
import io.ktor.http.HttpHeaders.ContentType as ContentTypeHeader
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.jackson3.jackson
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationServer
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.jacksonMapperBuilder

internal class ValidateDataApiTest {

    companion object {
        private val jsonMapper: JsonMapper = jacksonMapperBuilder().build()

        private val database = TestDB()

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            database.connection.dropData()
            database.stop()
        }
    }

    @Test
    internal fun `Returns OK when input it DATA`() {
        testApplication {
            application {
                routing { registerValidateDataApi(database) }

                install(ContentNegotiationServer) {
                    jackson {
                    }
                }
            }

            val validationData = ValidationData("DATA")

            val response =
                client.post("/v1/validate") {
                    header(ContentTypeHeader, ContentType.Application.Json)
                    header(AcceptHeader, ContentType.Application.Json)
                    setBody(jsonMapper.writeValueAsString(validationData))
                }

            assertEquals(response.status, HttpStatusCode.OK)
            assertEquals(
                response.bodyAsText(),
                jsonMapper.writeValueAsString(ValidationResult("OK")),
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
                    }
                }
            }

            val validationData = ValidationData("DATA1")

            val client = createClient {
                install(ContentNegotiationClient) {
                    jackson {
                    }
                }
            }

            val response =
                client.post("/v1/validate") {
                    accept(ContentType.Application.Json)
                    contentType(ContentType.Application.Json)
                    setBody(jsonMapper.writeValueAsString(validationData))
                }

            assertEquals(response.status, HttpStatusCode.OK)
            assertEquals(response.body<ValidationResult>(), ValidationResult("INVALID"))
        }
    }
}
