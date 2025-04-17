package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideWithRideOffer
import kotlinx.coroutines.flow.Flow

typealias RideWithRideOfferListResponse = Response<List<RideWithRideOffer>>
typealias RideListResponse = Response<List<Ride>>
typealias RideResponse = Response<Ride>
typealias AddRideResponse = Response<Unit>
typealias UpdateRideResponse = Response<Unit>

interface RideRepository {
    fun getRideListFlowGivenPassenger(passengerId: String): Flow<RideListResponse>

    suspend fun getRideListGivenPassenger(passengerId: String): RideWithRideOfferListResponse

    suspend fun getRideListGivenDriver(driverId: String): RideListResponse

    suspend fun getRide(rideId: String): RideResponse

    suspend fun addRide(ride: Ride): AddRideResponse

    suspend fun updateRide(rideId: String, rideUpdate: Map<String, String>): UpdateRideResponse
}