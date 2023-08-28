package io.github.mikaojk.services

import io.github.mikaojk.TestDB
import io.github.mikaojk.dropData
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ValidateServiceTest {

    private val database = TestDB()

    @Test
    internal fun `Should return validation result INVALID when input is NOT OK`() {

        val expectedValidationResult = ValidationResult("INVALID")

        val validationData = ValidationData(data = "NOT OK")
        val actualValidateData = ValidateService().executeValidateData(validationData, database)

        assertEquals(expectedValidationResult, actualValidateData)
    }

    @AfterAll
    internal fun afterAll() {
        database.connection.dropData()
        database.stop()
    }
}
