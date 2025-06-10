
package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location

typealias LocationResponse = Response<Location>
typealias LocationListResponse = Response<List<Location>>

interface LocationRepository {
    suspend fun getLocation(locationId: String): LocationResponse
    suspend fun getLocationList(): LocationListResponse
    suspend fun getLocationListByIds(locationIds: List<String>): LocationListResponse
}