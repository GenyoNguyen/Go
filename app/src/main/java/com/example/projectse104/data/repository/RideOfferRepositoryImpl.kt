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
import android.util.Log
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.repository.AddRideOfferResponse
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone
class RideOfferRepositoryImpl @Inject constructor(
    private val rideOffersRef: PostgrestQueryBuilder
) : RideOfferRepository {

    override suspend fun getRideOffer(rideOfferId: String): RideOfferResponse = try {
        val rideOffer = rideOffersRef.select {
            filter {
                eq("id", rideOfferId)
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

    override suspend fun getRideOfferListByUserId(
        userId: String,
        state: String
    ): RideOfferListResponse =
        try {
            val rideOffers = rideOffersRef.select {
                filter {
                    eq("userId", userId)
                    eq("status", state)
                }
            }.decodeList<RideOffer>()
            Response.Success(rideOffers)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    override suspend fun getRideOfferListByOtherUser(
        userId: String,
        state: String
    ): RideOfferListResponse =
        try {
            val rideOffers = rideOffersRef.select {
                filter {
                    neq("userId", userId)
                    eq("status", state)
                }
            }.decodeList<RideOffer>()
            Response.Success(rideOffers)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    override suspend fun addRideOffer(rideOffer: RideOffer): AddRideOfferResponse = try {
        rideOffersRef.insert(rideOffer)
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }


    override suspend fun getAcceptedRideOfferList(userId: String): RideOfferListResponse =
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
    override suspend fun updateRideOfferStatus(
        rideOfferId: String,
        status: String
    ): Response<Unit> =
        try {
            val currentTimeMillis = System.currentTimeMillis()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = currentTimeMillis
            val formatter = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
            formatter.timeZone = java.util.TimeZone.getTimeZone("Asia/Bangkok") // +07:00
            val isoTime = formatter.format(calendar.time) // e.g., "2025-05-16T22:24:00+07:00"
            android.util.Log.d("UpdateRideOffer", "Current time: $isoTime")

            rideOffersRef.update(
                update = {
                    set("status", status)
                    set("acceptedTime", isoTime)
                },
                request = {
                    filter {
                        eq("id", rideOfferId)
                    }
                }
            )
            Response.Success(Unit)
        } catch (e: Exception) {
            android.util.Log.e("UpdateRideOffer", "Error: $e")
            Response.Failure(e)
        }
}
