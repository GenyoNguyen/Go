package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.UserFavouriteRider

typealias UserFavouriteRiderListResponse = Response<List<UserFavouriteRider>>

interface UserFavouriteRiderRepository {
    suspend fun getRiderListFromUserFavouriteRider(userId: String): UserListResponse
}