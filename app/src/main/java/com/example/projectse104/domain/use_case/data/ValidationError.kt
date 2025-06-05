package com.example.projectse104.domain.use_case.data

enum class ValidationError {
    EMPTY_FIELD,
    INVALID_EMAIL,
    INVALID_PASSWORD,
    INVALID_PHONE_NUMBER,
    MISSING,
    TOO_SHORT,
    NOT_MATCH,
    CANT_GET_PIC,
}