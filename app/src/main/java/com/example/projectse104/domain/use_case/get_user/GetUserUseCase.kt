package com.example.projectse104.domain.use_case.get_user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UserRepository
import com.example.projectse104.domain.repository.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String): Flow<UserResponse> = flow {
        emit(Response.Loading)
        emit(repository.getUser(userId))
    }
}