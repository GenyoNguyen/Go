package com.example.projectse104.domain.use_case.data

import android.util.Patterns
import java.util.regex.Pattern

// Define an interface for email validation
interface PatternValidator {
    fun matches(email: String): Boolean
}

// Default implementation using Patterns.EMAIL_ADDRESS
class EmailPatternValidator(
    private val pattern: Pattern = Patterns.EMAIL_ADDRESS
) : PatternValidator {
    override fun matches(email: String): Boolean {
        return pattern.matcher(email).matches()
    }
}