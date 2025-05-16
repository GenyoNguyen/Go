package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import kotlinx.coroutines.flow.Flow

typealias RideOfferListResponse = Response<List<RideOffer>>
typealias RideOfferResponse = Response<RideOffer>
typealias AcceptRideOfferResponse = Response<Unit>

interface RideOfferRepository {
    suspend fun getRideOffer(rideOfferId: String): RideOfferResponse

    suspend fun getRideOfferList(): RideOfferListResponse

    suspend fun getRideOfferListByUserId(userId: String,state:String): RideOfferListResponse

    suspend fun getRideOfferListByOtherUser(userId: String,state: String): RideOfferListResponse

    suspend fun getAcceptedRideOfferList(userId: String): RideOfferListResponse
}