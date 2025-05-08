package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideWithRideOffer
import kotlinx.coroutines.flow.Flow

typealias RideResponse = Response<Ride>
typealias RideListResponse = Response<List<Ride>>
typealias AddRideResponse = Response<Unit>
typealias UpdateRideResponse = Response<Unit>
typealias RideWithRideOfferListResponse = Response<List<RideWithRideOffer>>

interface RideRepository {
    fun getRideListFlowGivenPassenger(passengerId: String): Flow<RideListResponse>

    suspend fun getRideListGivenPassenger(passengerId: String): RideListResponse

    suspend fun getRideListGivenDriver(driverId: String): RideListResponse

    suspend fun getRide(rideId: String): RideResponse

    suspend fun addRide(ride: Ride): AddRideResponse

    suspend fun updateRide(rideId: String, rideUpdate: Map<String, String>): UpdateRideResponse
}