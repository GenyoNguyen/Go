package com.example.projectse104.domain.use_case

import com.example.projectse104.domain.use_case.user.GetUserUseCase
import com.example.projectse104.fakes.FakeUserRepository
import org.junit.Before
import org.junit.Test

class GetUserUseCaseTest {

    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setUp() {
        getUserUseCase = GetUserUseCase(FakeUserRepository())
    }

    @Test
    fun invoke() {
        val result = getUserUseCase("123")
    }
}