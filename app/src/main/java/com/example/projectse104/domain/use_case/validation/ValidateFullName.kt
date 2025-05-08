package com.example.projectse104.domain.use_case.validation

import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.data.ValidationResult

class ValidateFullName {

    fun execute(fullName: String): ValidationResult {
        if (fullName.isBlank()) {
            return ValidationResult(
                successful = false,
                error = ValidationError.EMPTY_FIELD
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}