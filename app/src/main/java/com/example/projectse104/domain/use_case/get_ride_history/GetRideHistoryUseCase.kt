package com.example.projectse104.domain.use_case.get_ride_history

import com.example.projectse104.core.Response
import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRideHistoryUseCase @Inject constructor(
    private val repository: RideRepository
) {
    operator fun invoke(passengerId: String): Flow<RideListResponse> = flow {
        emit(Response.Loading)
        emit(repository.getRideHistoryList(passengerId))
    }
}