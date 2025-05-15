package com.example.projectse104.domain.use_case.validation

import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.data.ValidationResult

class ValidateConfirmPassword {
    fun execute(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isBlank()) {
            return ValidationResult(
                successful = false,
                error = ValidationError.MISSING
            )
        }
        if (confirmPassword != password) {
            return ValidationResult(
                successful = false,
                error = ValidationError.NOT_MATCH
            )
        }
        return ValidationResult(successful = true)
    }
}