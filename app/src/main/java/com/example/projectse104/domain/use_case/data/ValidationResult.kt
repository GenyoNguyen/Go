package com.example.projectse104.domain.use_case.data

data class ValidationResult(
    val successful: Boolean,
    val error: ValidationError? = null
)
