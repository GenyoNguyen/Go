package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import kotlinx.coroutines.flow.Flow

typealias RideListResponse = Response<List<Ride>>
typealias RideResponse = Response<Ride>

interface RideRepository {
    fun getPendingRideList(passengerId: String): Flow<RideListResponse>

    suspend fun getRideHistoryList(passengerId: String): Flow<List<Ride>>

    suspend fun getRide(rideId: String): RideResponse
}