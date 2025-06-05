package com.example.projectse104.domain.use_case.user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UploadProfilePicResponse
import com.example.projectse104.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UploadProfilePicUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String, imageUri: String): Flow<UploadProfilePicResponse> = flow {
        emit(Response.Loading)
        emit(repository.uploadProfilePic(userId, imageUri))
    }
}