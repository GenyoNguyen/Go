package com.example.projectse104.domain.use_case.validation

import com.example.projectse104.domain.use_case.data.EmailPatternValidator
import com.example.projectse104.domain.use_case.data.PatternValidator
import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.data.ValidationResult

class ValidateEmail(private val patternValidator: PatternValidator = EmailPatternValidator()) {

    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                error = ValidationError.EMPTY_FIELD
            )
        }
        if (!patternValidator.matches(email)) {
            return ValidationResult(
                successful = false,
                error = ValidationError.INVALID_EMAIL
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}