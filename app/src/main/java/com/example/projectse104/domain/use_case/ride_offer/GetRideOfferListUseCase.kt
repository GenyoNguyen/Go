package com.example.projectse104.domain.use_case.ride_offer

import com.example.projectse104.domain.repository.RideOfferListResponse
import com.example.projectse104.domain.repository.RideOfferRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRideOfferListUseCase @Inject constructor(
    private val repository: RideOfferRepository
) {
    operator fun invoke(): Flow<RideOfferListResponse> = repository.getRideOfferList()
}