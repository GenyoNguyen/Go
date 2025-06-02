package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location
import com.example.projectse104.domain.model.RideOffer

typealias LocationResponse = Response<Location>
typealias LocationListResponse = Response<List<Location>>

interface LocationRepository {
    suspend fun getLocation(locationId: String): LocationResponse
    suspend fun getLocationList(): LocationListResponse
}