package com.example.projectse104.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectse104.core.Response
import com.example.projectse104.core.RideStatus
import com.example.projectse104.core.toCustomString
import com.example.projectse104.domain.model.Ride
import com.example.projectse104.domain.model.RideOffer // Giả sử model này tồn tại
import com.example.projectse104.domain.use_case.ride_offer.UpdateRideOfferStatusUseCase
import com.example.projectse104.domain.use_case.ride.AddNewRideUseCase
import com.example.projectse104.domain.use_case.ride_offer.GetRideOfferUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmRideScreenViewModel @Inject constructor(
    private val updateRideOfferStatusUseCase: UpdateRideOfferStatusUseCase,
    private val addNewRideUseCase: AddNewRideUseCase,
    private val getRideOfferUseCase: GetRideOfferUseCase
) : ViewModel() {

    private val _updateStatusState = MutableStateFlow<Response<Unit>?>(null)
    val updateStatusState = _updateStatusState.asStateFlow()

    fun updateRideOfferStatusAndAddNewRide(rideId: String, userId: String) {
        viewModelScope.launch {
            // Bước 1: Cập nhật status của rideOffer hiện tại
            updateRideOfferStatusUseCase(rideId, "ACCEPTED").collect { updateResponse ->
                when (updateResponse) {
                    is Response.Success -> {
                        // Bước 2: Thêm ride mới nếu cập nhật thành công
                        val newRide = createNewRide(rideId, userId)
                        addNewRideUseCase(newRide).collect { addResponse ->
                            when (addResponse) {
                                is Response.Success -> {
                                    _updateStatusState.value = Response.Success(Unit)
                                }
                                is Response.Failure -> {
                                    _updateStatusState.value = Response.Failure(addResponse.e)
                                }
                                else -> {} // Bỏ qua Response.Loading
                            }
                        }
                    }
                    is Response.Failure -> {
                        _updateStatusState.value = Response.Failure(updateResponse.e)
                    }
                    else -> {} // Bỏ qua Response.Loading
                }
            }
        }
    }

    private suspend fun createNewRide(rideId: String, userId: String): Ride {
        val rideOfferResponse = getRideOfferUseCase(rideId).first()
        val departTime = if (rideOfferResponse is Response.Success) {
            val formatter = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
            formatter.timeZone = java.util.TimeZone.getTimeZone("Asia/Bangkok")
            formatter.parse(rideOfferResponse.data?.rideOffer?.estimatedDepartTime.toCustomString()) // Chuyển String thành Date
        } else {
            java.util.Date(System.currentTimeMillis()) // Fallback: 12:03 AM +07 (~1742332980000)
        }

        return Ride(
            id = UUID.randomUUID().toString(),
            rideOfferId = rideId,
            passengerId = userId,
            departTime = departTime, // Bây giờ là Date?
            status = RideStatus.PENDING,
        )
    }

    fun fetchRideDetails(rideId: String, riderName: String) {
        // Triển khai logic để tải dữ liệu nếu cần
    }
}