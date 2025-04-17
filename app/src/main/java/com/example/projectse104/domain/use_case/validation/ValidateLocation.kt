package com.example.projectse104.domain.use_case.validation

import com.example.projectse104.domain.use_case.data.ValidationResult

class ValidateLocation {

    fun execute(location: String): ValidationResult {
        return ValidationResult(
            successful = true
        )
    }
}