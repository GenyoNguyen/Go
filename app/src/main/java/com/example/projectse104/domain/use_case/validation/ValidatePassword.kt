package com.example.projectse104.domain.use_case.validation

import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.data.ValidationResult

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                error = ValidationError.MISSING
            )
        }
        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                error = ValidationError.TOO_SHORT
            )
        }
        return ValidationResult(successful = true)
    }
}