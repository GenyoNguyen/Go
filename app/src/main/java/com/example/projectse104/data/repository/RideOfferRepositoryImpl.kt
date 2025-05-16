package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.repository.AcceptRideOfferResponse
import com.example.projectse104.domain.repository.RideOfferListResponse
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.RideOfferResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class RideOfferRepositoryImpl @Inject constructor(
    private val rideOffersRef: PostgrestQueryBuilder
) : RideOfferRepository {

    override suspend fun getRideOffer(rideOfferId: String): RideOfferResponse = try {
        val rideOffer = rideOffersRef.select {
            filter {
                eq("id",rideOfferId)
            }
        }.decodeSingle<RideOffer>()
        Response.Success(rideOffer)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun getRideOfferList(): RideOfferListResponse =
        try {
            val rideOffers = rideOffersRef.select().decodeList<RideOffer>()
            Response.Success(rideOffers)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    override suspend fun getRideOfferListByUserId(userId: String,state:String): RideOfferListResponse =
        try {
            val rideOffers = rideOffersRef.select {
                filter {
                    eq("userId", userId)
                    eq("status",state)
                }
            }.decodeList<RideOffer>()
            Response.Success(rideOffers)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    override suspend fun getRideOfferListByOtherUser(userId: String,state: String): RideOfferListResponse =
        try {
            val rideOffers = rideOffersRef.select {
                filter {
                    neq("userId", userId)
                    eq("status",state)
                }
            }.decodeList<RideOffer>()
            Response.Success(rideOffers)
        } catch (e: Exception) {
            Response.Failure(e)
        }


    override suspend fun getAcceptedRideOfferList(userId: String):RideOfferListResponse =
        try {
            val rideOffers = rideOffersRef.select {
                filter {
                    eq("userId", userId)
                    eq("isAccepted", true)
                }
            }.decodeList<RideOffer>()
            Response.Success(rideOffers)
        } catch (e: Exception) {
            Response.Failure(e)
        }

}