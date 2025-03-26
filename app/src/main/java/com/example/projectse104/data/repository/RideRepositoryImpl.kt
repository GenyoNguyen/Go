package com.example.projectse104.data.repository

import com.example.projectse104.core.DEPART_TIME_FIELD
import com.example.projectse104.core.PASSENGER_ID_FIELD
import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.core.STATUS_FIELD
import com.example.projectse104.domain.model.toRide
import com.example.projectse104.domain.repository.RideListResponse
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.RideResponse
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query.Direction
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RideRepositoryImpl(
    private val ridesRef: CollectionReference
) : RideRepository {
    override fun getPendingRideList(passengerId: String): Flow<RideListResponse> = callbackFlow {
        val listener = ridesRef
            .whereEqualTo(STATUS_FIELD, RideStatus.PENDING.value)
            .orderBy(DEPART_TIME_FIELD, Direction.DESCENDING)
            .addSnapshotListener { rideListSnapshot, e ->
                val rideListResponse = if (rideListSnapshot != null) {
                    val rideList = rideListSnapshot.map { rideSnapshot ->
                        rideSnapshot.toRide()
                    }
                    Response.Success(rideList)
                } else {
                    Response.Failure(e)
                }
                trySend(rideListResponse)
            }
        awaitClose {
            listener.remove()
        }
    }

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