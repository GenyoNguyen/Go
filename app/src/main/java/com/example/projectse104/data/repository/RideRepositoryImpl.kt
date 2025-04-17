package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideWithRideOffer
import com.example.projectse104.domain.repository.AddRideResponse
import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.RideResponse
import com.example.projectse104.domain.repository.RideWithRideOfferListResponse
import com.example.projectse104.domain.repository.UpdateRideResponse
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.postgresListDataFlow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class RideRepositoryImpl(
    private val ridesRef: PostgrestQueryBuilder,
    private val rideChannel: RealtimeChannel
) : RideRepository {
    override fun getRideListFlowGivenPassenger(passengerId: String): Flow<RideListResponse> =
        callbackFlow {
            val rideListFlow = rideChannel.postgresListDataFlow(
                primaryKey = Ride::id,
                schema = "public",
                table = "Ride"
            )

            rideListFlow.collect {
                trySend(Response.Success(it))
            }

            rideChannel.subscribe()
            awaitClose {
                launch {
                    rideChannel.unsubscribe()
                }
            }
        }

    override suspend fun getRideListGivenPassenger(passengerId: String): RideWithRideOfferListResponse =
        try {
            val columns = Columns.list(
                "id", "rideOfferId", "rideOffer:RideOffer(*)",
                "passengerId", "departTime",
                "arriveTime", "status", "rating", "comment"
            )

            val rideList = ridesRef.select(columns = columns) {
                filter {
                    Ride::passengerId eq passengerId
                }
                order(column = Ride::departTime.toString(), order = Order.DESCENDING)
            }.decodeList<RideWithRideOffer>()
            Response.Success(rideList)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    override suspend fun getRideListGivenDriver(driverId: String): RideListResponse {
        TODO("Not yet implemented")
    }
//
//    override suspend fun getRideListGivenDriver(driverId: String): RideListResponse = try {
//        val rideList = ridesRef.select {
//            filter {
//                Ride::driverId eq driverId
//            }
//            order(column = Ride::departTime.toString(), order = Order.DESCENDING)
//        }.decodeList<Ride>()
//
//        Response.Success(rideList)
//    } catch (e: Exception) {
//        Response.Failure(e)
//    }


    override suspend fun getRide(rideId: String): RideResponse = try {
        val ride = ridesRef.select {
            filter {
                Ride::id eq rideId
            }
        }.decodeSingle<Ride>()
        Response.Success(ride)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun addRide(ride: Ride): AddRideResponse = try {
        ridesRef.insert(ride)
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun updateRide(
        rideId: String,
        rideUpdate: Map<String, String>
    ): UpdateRideResponse = try {
        ridesRef.update({
            for ((key, value) in rideUpdate) {
                set(key, value)
            }
        }) {
            filter {
                Ride::id eq rideId
            }
        }
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}