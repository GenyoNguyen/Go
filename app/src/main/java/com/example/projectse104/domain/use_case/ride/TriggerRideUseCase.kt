package com.example.projectse104.domain.use_case.ride

import android.util.Log
import com.example.projectse104.domain.repository.RideRepository
import com.example.projectse104.domain.repository.UpdateRideResponse
import javax.inject.Inject

class TriggerRideUseCase @Inject constructor(
    private val rideRepository: RideRepository
) {
    suspend operator fun invoke(rideId: String, isStart: Boolean): UpdateRideResponse {
        Log.d("TriggerRideUseCase", "Triggering ride with ID: $rideId, isStart: $isStart")
        return rideRepository.updateRide(
            rideId,
            mapOf("status" to if (isStart) "ONGOING" else "SUCCESS")
        )
    }
}
