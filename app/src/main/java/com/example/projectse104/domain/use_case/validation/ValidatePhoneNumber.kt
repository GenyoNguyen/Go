package com.example.projectse104.domain.use_case.validation

import android.util.Patterns
import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.data.ValidationResult

class ValidatePhoneNumber {

    fun execute(phoneNumber: String): ValidationResult {
        if (phoneNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                error = ValidationError.EMPTY_FIELD
            )
        }
        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            return ValidationResult(
                successful = false,
                error = ValidationError.INVALID_PHONE_NUMBER
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}