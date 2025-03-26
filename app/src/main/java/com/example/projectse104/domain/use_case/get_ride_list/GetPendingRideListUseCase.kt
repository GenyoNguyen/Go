package com.example.projectse104.domain.use_case.get_ride_list

import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPendingRideListUseCase @Inject constructor(
    private val repository: RideRepository
) {
    operator fun invoke(passengerId: String): Flow<RideListResponse> =
        repository.getPendingRideList(passengerId)
}