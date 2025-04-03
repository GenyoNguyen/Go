package com.example.projectse104.data.repository

import com.example.projectse104.core.DEPART_TIME_FIELD
import com.example.projectse104.core.PASSENGER_ID_FIELD
import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.core.STATUS_FIELD
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.toRide
import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.RideResponse
import com.google.firebase.firestore.Query.Direction
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class RideRepositoryImpl(
    private val ridesRef: PostgrestQueryBuilder
) : RideRepository {
    @OptIn(SupabaseExperimental::class)
    override fun getPendingRideList(passengerId: String): Flow<List<Ride>> =
        ridesRef.selectAsFlow(
            Ride::id,
            filter = FilterOperation(
                Ride::status.toString(), FilterOperator.EQ, RideStatus.PENDING.value
            )
        )


//    override fun getPendingRideList(passengerId: String): Flow<RideListResponse> = callbackFlow {
//        val listener = ridesRef
//            .whereEqualTo(STATUS_FIELD, RideStatus.PENDING.value)
//            .orderBy(DEPART_TIME_FIELD, Direction.DESCENDING)
//            .addSnapshotListener { rideListSnapshot, e ->
//                val rideListResponse = if (rideListSnapshot != null) {
//                    val rideList = rideListSnapshot.map { rideSnapshot ->
//                        rideSnapshot.toRide()
//                    }
//                    Response.Success(rideList)
//                } else {
//                    Response.Failure(e)
//                }
//                trySend(rideListResponse)
//            }
//        awaitClose {
//            listener.remove()
//        }
//    }

    override suspend fun getRideHistoryList(passengerId: String): RideListResponse = try {
        val rideListSnapshot = ridesRef
            .whereEqualTo(PASSENGER_ID_FIELD, passengerId)
            .whereEqualTo(STATUS_FIELD, RideStatus.SUCCESS.value)
            .orderBy(DEPART_TIME_FIELD, Direction.DESCENDING)
            .get()
            .await()
        val rideListResponse = rideListSnapshot.map { rideSnapshot ->
            rideSnapshot.toRide()
        }
        Response.Success(rideListResponse)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun getRide(rideId: String): RideResponse = try {
        val rideSnapshot = ridesRef.document(rideId).get().await()
        Response.Success(rideSnapshot.toRide())
    } catch (e: Exception) {
        Response.Failure(e)
    }
}