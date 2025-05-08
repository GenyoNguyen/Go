package com.example.projectse104.domain.use_case

import com.example.projectse104.domain.use_case.data.ValidationError
import com.example.projectse104.domain.use_case.validation.ValidateFullName
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateFullNameTest {

    private lateinit var validateFullName: ValidateFullName

    @Before
    fun setUp() {
        validateFullName = ValidateFullName()
    }

    @Test
    fun `Empty full name returns error`() {
        val result = validateFullName.execute("")

        assertEquals(result.successful, false)
        assertEquals(result.error, ValidationError.EMPTY_FIELD)
    }

    @Test
    fun `Valid full name returns success`() {
        val result = validateFullName.execute("Valid Full Name")

        assertEquals(result.successful, true)
    }
}