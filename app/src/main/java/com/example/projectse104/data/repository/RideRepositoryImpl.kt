package com.example.projectse104.data.repository

import android.util.Log
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.repository.AddRideResponse
import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.RideResponse
import com.example.projectse104.domain.repository.UpdateRideResponse
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

    override suspend fun getRideListGivenPassenger(passengerId: String): RideListResponse =
        try {
            println("Lmao")
            val rideList = ridesRef.select {
                filter {
                    eq("passengerId", passengerId)
                }
                order(column = "departTime", order = Order.DESCENDING)
            }.decodeList<Ride>()
            Response.Success(rideList)
        } catch (e: Exception) {
            println("Exception: $e")
            Response.Failure(e)
        }

    override suspend fun getRideListGivenPassengerPaginated(
        userId: String,
        from: Long,
        to: Long
    ): Response<List<Ride>> =
        try {
            println("Fetching rides for passengerId: $userId, from: $from, to: $to")
            val rideList = ridesRef.select {
                filter {
                    eq("passengerId", userId)
                }
                order(column = "departTime", order = Order.DESCENDING)
                range(from, to)
            }.decodeList<Ride>()
            println("Fetched ${rideList.size} rides")
            Response.Success(rideList)
        } catch (e: Exception) {
            println("Exception: $e")
            Response.Failure(e)
        }

    override suspend fun getRideListByRideOfferIds(
        rideOfferIds: List<String>,
        from: Long,
        to: Long
    ): RideListResponse =
        try {
            println("Fetching rides for rideOfferIds: $rideOfferIds, from: $from, to: $to")
            val rides = if (rideOfferIds.isNotEmpty()) {
                ridesRef.select {
                    filter {
                        isIn("rideOfferId", rideOfferIds)
                    }
                    order(column = "departTime", order = Order.DESCENDING)
                    range(from, to)
                }.decodeList<Ride>()
            } else {
                emptyList()
            }
            println("RIDE IDS IN THIS FUNCTION: ${rides.map { it.id }}")
            Response.Success(rides)
        } catch (e: Exception) {
            println("Exception: $e")
            Response.Failure(e)
        }

    override suspend fun getSuccessRideList(): RideListResponse =
        try {
            val rides =
                ridesRef.select {
                    filter {
                        eq("status", "SUCCESS")
                    }
                    order(column = "departTime", order = Order.DESCENDING)
                }.decodeList<Ride>()
            Response.Success(rides)
        } catch (e: Exception) {
            println("Exception: $e")
            Response.Failure(e)
        }

    override suspend fun getRideListGivenDriver(driverId: String): RideListResponse =
        try {
            println("Lmao")
            val rideList = ridesRef.select {
                filter {
                    eq("driverId", driverId)
                }
                order(column = "departTime", order = Order.DESCENDING)
            }.decodeList<Ride>()
            Response.Success(rideList)
        } catch (e: Exception) {
            println("Exception: $e")
            Response.Failure(e)
        }

    override suspend fun getRide(rideId: String): RideResponse = try {
        val ride = ridesRef.select {
            filter {
                eq("id", rideId)
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
        Log.d("RideRepository", "Updating ride with map: $rideUpdate")
        val user = ridesRef.update({
            for ((key, value) in rideUpdate) {
                if(key=="rating") {
                    set(key, value.toFloat())
                }
                else {
                    set(key, value)
                }
            }
        }) {
            filter {
                eq("id", rideId)
            }
        }.decodeSingle<User>()
        Log.d("RideRepository", "Updated ride: $user")
        Response.Success(Unit)
    } catch (e: Exception) {
        Log.d("RideRepository", "Cannot updated ride: ${e.message}")
        Response.Failure(e)
    }
}