package io.github.mikaojk.services

import io.github.mikaojk.TestDB
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ValidateServiceTest {

    private val database = TestDB()

    @Test
    internal fun `Should return validation result INVALID when input is NOT OK`() {

        val expectedValidationResult = ValidationResult("INVALID")

        val validationData = ValidationData(data = "NOT OK")
        val actualValidateData = ValidateService().executeValidateData(validationData, database)

        assertEquals(expectedValidationResult, actualValidateData)
    }

}
