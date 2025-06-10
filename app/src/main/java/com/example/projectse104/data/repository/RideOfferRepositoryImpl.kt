package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.repository.AcceptRideOfferResponse
import com.example.projectse104.domain.repository.RideOfferListResponse
import com.example.projectse104.domain.repository.RideOfferRepository
import com.example.projectse104.domain.repository.AddRideOfferResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import android.util.Log
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.repository.RideOfferResponse
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

    override suspend fun getRideOfferList(): RideOfferListResponse = try {
        val rideOffers = rideOffersRef.select().decodeList<RideOffer>()
        Response.Success(rideOffers)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun getRideOfferListByUserId(
        userId: String,
        state: String
    ): RideOfferListResponse = try {
        println("Fetching ride offers for userId: $userId, state: $state")
        val rideOffers = rideOffersRef.select {
            filter {
                eq("userId", userId)
                eq("status", state)
            }
            order(column = "requestTime", order = Order.ASCENDING)
        }.decodeList<RideOffer>()
        println("Fetched ${rideOffers.size} ride offers")
        Response.Success(rideOffers)
    } catch (e: Exception) {
        println("Exception: $e")
        Response.Failure(e)
    }

    override suspend fun getRideOfferListByUserIdPaginated(
        userId: String,
        state: String,
        from: Long,
        to: Long
    ): Response<List<RideOffer>> = try {
        println("Fetching ride offers for userId: $userId, state: $state, from: $from, to: $to")
        val rideOffers = rideOffersRef.select {
            filter {
                eq("userId", userId)
                eq("status", state)
            }
            order(column = "requestTime", order = Order.ASCENDING)
            range(from, to)
        }.decodeList<RideOffer>()
        println("Fetched ${rideOffers.size} ride offers")
        Response.Success(rideOffers)
    } catch (e: Exception) {
        println("Exception: $e")
        Response.Failure(e)
    }

    override suspend fun getRideOfferListByOtherUser(
        userId: String,
        state: String,
        page: Int,
        limit: Int
    ): Response<List<RideOffer>> = try {
        val from = (page * limit).toLong()
        val to = (from + limit - 1).toLong()
        val rideOffers = rideOffersRef.select {
            filter {
                neq("userId", userId)
                eq("status", state)
            }
            order("estimatedDepartTime", order = Order.ASCENDING)
            range(from, to)
        }.decodeList<RideOffer>()
        println("Loaded ${rideOffers.size} ride offers for page $page")
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

    override suspend fun getAcceptedRideOfferList(userId: String): RideOfferListResponse = try {
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
    override suspend fun getSuccessRideOfferList(): RideOfferListResponse = try {
        val rideOffers = rideOffersRef.select {
            filter {
                eq("status", "ACCEPTED")
            }
        }.decodeList<RideOffer>()
        Response.Success(rideOffers)
    } catch (e: Exception) {
        Response.Failure(e)
    }
    override suspend fun updateRideOfferStatus(
        rideOfferId: String,
        status: String
    ): Response<Unit> = try {
        val currentTimeMillis = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        formatter.timeZone = TimeZone.getTimeZone("Asia/Bangkok")
        val isoTime = formatter.format(calendar.time)
        Log.d("UpdateRideOffer", "Current time: $isoTime")

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
        Log.e("UpdateRideOffer", "Error: $e")
        Response.Failure(e)
    }
}