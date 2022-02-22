package io.github.mikaojk.services

import io.github.mikaojk.db.DatabaseInterface
import io.github.mikaojk.db.validData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope


class ValidateService {
    @OptIn(DelicateCoroutinesApi::class)
    fun executeValidateData(validationData: ValidationData, database: DatabaseInterface): ValidationResult =
        with(GlobalScope) {
            if (database.validData(validationData.data)) {
                return ValidationResult("OK")
            }
            return ValidationResult("INVALID")
        }
}

data class ValidationData(
    val data: String
)

data class ValidationResult(
    val result: String
)

