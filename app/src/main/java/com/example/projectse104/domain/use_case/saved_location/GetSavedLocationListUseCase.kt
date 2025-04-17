package com.example.projectse104.domain.use_case.saved_location

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.UserLocationRepository
import com.example.projectse104.domain.repository.UserLocationWithLocationListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSavedLocationListUseCase @Inject constructor(
    private val userLocationRepository: UserLocationRepository
) {
    operator fun invoke(userId: String): Flow<UserLocationWithLocationListResponse> = flow {
        emit(Response.Loading)
        emit(userLocationRepository.getSavedLocationList(userId))
    }
}