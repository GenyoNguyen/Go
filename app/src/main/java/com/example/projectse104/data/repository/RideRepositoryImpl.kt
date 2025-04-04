package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.RideResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
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
    override fun getPendingRideList(passengerId: String): Flow<RideListResponse> = callbackFlow {
        val rideListFlow = rideChannel.postgresListDataFlow(
            primaryKey = Ride::id,
            schema = "public",
            table = "Ride",
            filter = FilterOperation(Ride::status.toString(), FilterOperator.EQ, RideStatus.PENDING)
        )

        rideListFlow.collect {
            if (it.isNotEmpty()) {
                trySend(Response.Success(it))
            } else {
                trySend(Response.Failure(Exception("No rides found")))
            }

        }

        rideChannel.subscribe()
        awaitClose {
            launch {
                rideChannel.unsubscribe()
            }
        }
    }

    override suspend fun getRideHistoryList(passengerId: String): Flow<List<Ride>> {
        TODO("Not yet implemented")
    }


//    override suspend fun getRideHistoryList(passengerId: String): RideListResponse = try {
//        val rideListSnapshot = ridesRef
//            .whereEqualTo(PASSENGER_ID_FIELD, passengerId)
//            .whereEqualTo(STATUS_FIELD, RideStatus.SUCCESS.value)
//            .orderBy(DEPART_TIME_FIELD, Direction.DESCENDING)
//            .get()
//            .await()
//        val rideListResponse = rideListSnapshot.map { rideSnapshot ->
//            rideSnapshot.toRide()
//        }
//        Response.Success(rideListResponse)
//    } catch (e: Exception) {
//        Response.Failure(e)
//    }


    override suspend fun getRide(rideId: String): RideResponse {
        TODO("Not yet implemented")
    }

//    override suspend fun getRide(rideId: String): RideResponse = try {
//        val rideSnapshot = ridesRef.document(rideId).get().await()
//        Response.Success(rideSnapshot.toRide())
//    } catch (e: Exception) {
//        Response.Failure(e)
//    }
}