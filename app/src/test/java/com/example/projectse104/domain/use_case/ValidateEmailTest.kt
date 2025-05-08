package com.example.projectse104.domain.use_case

import com.example.projectse104.domain.use_case.data.PatternValidator
import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.validation.ValidateEmail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class ValidateEmailTest {

    private lateinit var validateEmail: ValidateEmail
    private val mockPatternValidator = mock<PatternValidator>()

    @Before
    fun setUp() {
        validateEmail = ValidateEmail(mockPatternValidator)
    }

    @Test
    fun `Invalid email returns false`() {
        `when`(mockPatternValidator.matches("invalid-email")).thenReturn(false)

        val result = validateEmail.execute("invalid-email")
        assert(!result.successful)
        assert(result.error == ValidationError.INVALID_EMAIL)
    }

    @Test
    fun `Empty email returns error`() {
        val result = validateEmail.execute("")
        assert(!result.successful)
        assert(result.error == ValidationError.EMPTY_FIELD)
    }

    @Test
    fun `Valid email returns success`() {
        `when`(mockPatternValidator.matches("abc@gmail.com")).thenReturn(true)

        val result = validateEmail.execute("abc@gmail.com")
        assert(result.successful)
        assert(result.error == null)
    }
}