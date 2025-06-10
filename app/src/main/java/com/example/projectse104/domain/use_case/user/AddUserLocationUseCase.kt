package com.example.projectse104.domain.use_case.user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.UserLocation
import com.example.projectse104.domain.repository.UserLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddUserLocationUseCase @Inject constructor(
    private val userLocationRepository: UserLocationRepository
) {
    operator fun invoke(userLocation: UserLocation): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            userLocationRepository.addUserLocation(userLocation)
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Failure(e))
        }
    }
}