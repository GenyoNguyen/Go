package com.example.projectse104.domain.use_case.location


import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location
import com.example.projectse104.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<Response<List<Location>>> = flow {
        emit(Response.Loading)
        try {
            val locationList = locationRepository.getLocationList()
            if (locationList is Response.Success) {
                emit(Response.Success(locationList.data))
            } else {
                emit(Response.Failure((locationList as Response.Failure).e))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}