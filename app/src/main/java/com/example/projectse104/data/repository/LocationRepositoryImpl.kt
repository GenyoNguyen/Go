package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.Location
import com.example.projectse104.domain.model.RideOffer
import com.example.projectse104.domain.repository.LocationListResponse
import com.example.projectse104.domain.repository.LocationRepository
import com.example.projectse104.domain.repository.LocationResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LocationRepositoryImpl(
    private val locationRef: PostgrestQueryBuilder
) : LocationRepository {
    private val locationCache = mutableMapOf<String, Location>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getLocationList()
        }
    }
    override suspend fun getLocation(locationId: String): LocationResponse = try {
        locationCache[locationId]?.let {
            return Response.Success(it)
        }
        val location = locationRef.select {
            filter {
                eq("id", locationId)
            }
        }.decodeSingle<Location>()
        locationCache[locationId] = location
        Response.Success(location)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun getLocationList(): LocationListResponse = try {
        val locations = locationRef.select().decodeList<Location>()
        locations.forEach { locationCache[it.id] = it }
        Response.Success(locations)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun getLocationListByIds(locationIds: List<String>): LocationListResponse = try {
        val cachedLocations = locationIds.mapNotNull { locationCache[it] }
        val missingIds = locationIds.filter { !locationCache.containsKey(it) }
        if (missingIds.isEmpty()) {
            Response.Success(cachedLocations)
        } else {
            val newLocations = locationRef.select {
                filter { isIn("id", missingIds) }
            }.decodeList<Location>()
            newLocations.forEach { locationCache[it.id] = it }
            Response.Success(cachedLocations + newLocations)
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }
}