package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.model.UserLocation
import com.example.projectse104.domain.model.UserLocationWithLocation

typealias UserLocationWithLocationListResponse = Response<List<UserLocationWithLocation>>
typealias AddUserLocationResponse = Response<Unit>

interface UserLocationRepository {
    suspend fun getSavedLocationList(userId: String): UserLocationWithLocationListResponse
    suspend fun addUserLocation(userLocation: UserLocation): AddUserLocationResponse
}