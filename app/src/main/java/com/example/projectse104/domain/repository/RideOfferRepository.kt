package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer

typealias RideOfferListResponse = Response<List<RideOffer>>
typealias RideOfferResponse = Response<RideOffer>
typealias AcceptRideOfferResponse = Response<Unit>
typealias AddRideOfferResponse = Response<Unit>

interface RideOfferRepository {
    suspend fun getRideOffer(rideOfferId: String): RideOfferResponse

    suspend fun getRideOfferList(): RideOfferListResponse

    suspend fun getRideOfferListByUserId(userId: String, state: String): RideOfferListResponse

    suspend fun getRideOfferListByOtherUser(
        userId: String,
        state: String,
        page: Int,
        limit: Int
    ): RideOfferListResponse

    suspend fun getAcceptedRideOfferList(userId: String): RideOfferListResponse
    suspend fun getSuccessRideOfferList(): RideOfferListResponse
    suspend fun getRideOfferListByUserIdPaginated(
        userId: String,
        state: String,
        from: Long,
        to: Long
    ): RideOfferListResponse

    suspend fun updateRideOfferStatus(rideOfferId: String, status: String): Response<Unit>

    suspend fun addRideOffer(rideOffer: RideOffer): AddRideOfferResponse

    suspend fun deleteRideOffer(rideCode: String): Response<Unit>
}