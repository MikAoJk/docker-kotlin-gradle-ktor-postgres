package io.github.mikaojk.services

import io.github.mikaojk.db.DatabaseInterface
import io.github.mikaojk.db.validData

class ValidateService {
    fun executeValidateData(
        validationData: ValidationData,
        database: DatabaseInterface
    ): ValidationResult {
        if (database.validData(validationData.data)) {
            return ValidationResult("OK")
        }
        return ValidationResult("INVALID")
    }
}

data class ValidationData(val data: String)

data class ValidationResult(val result: String)
