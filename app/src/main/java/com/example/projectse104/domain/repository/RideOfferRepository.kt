package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import kotlinx.coroutines.flow.Flow

typealias RideOfferListResponse = Response<List<RideOffer>>
typealias RideOfferResponse = Response<RideOffer>
typealias AcceptRideOfferResponse = Response<Unit>
typealias AcceptedRideOfferListResponse = Response<List<RideOffer>>
typealias AddRideOfferResponse = Response<Unit>

interface RideOfferRepository {
    suspend fun getRideOffer(rideOfferId: String): RideOfferResponse

    fun getRideOfferList(): Flow<RideOfferListResponse>

    fun getAcceptedRideOfferList(userId: String): Flow<AcceptedRideOfferListResponse>

    suspend fun acceptRideOffer(rideOfferId: String): AcceptRideOfferResponse

    suspend fun addRideOffer(rideOffer: RideOffer): AddRideOfferResponse
}