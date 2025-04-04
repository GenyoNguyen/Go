package com.example.projectse104.domain.use_case.update_user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UpdateUserResponse
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userUpdate: Map<String, Any>): Flow<UpdateUserResponse> = flow {
        emit(Response.Loading)
        emit(repository.updateUser(userUpdate))
    }
}