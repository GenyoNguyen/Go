package com.example.projectse104.data.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.UserFavouriteRiderWithRider
import com.example.projectse104.domain.repository.UserFavouriteRiderRepository
import com.example.projectse104.domain.repository.UserListResponse
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder

class UserFavouriteRiderRepositoryImpl(
    private val userFavouriteRiderRef: PostgrestQueryBuilder
) : UserFavouriteRiderRepository {
    override suspend fun getRiderListFromUserFavouriteRider(userId: String): UserListResponse =
        try {

            val columns = Columns.list("userId", "riderId", "rider:User!riderId(*)")

            val favouriteRiderWithRiderList = userFavouriteRiderRef.select(columns = columns) {
                filter {
                    eq("userId", userId)
                }
            }.decodeList<UserFavouriteRiderWithRider>()

            val riderList = favouriteRiderWithRiderList.map { it.rider }
            Response.Success(riderList)
        } catch (e: Exception) {
            Response.Failure(e)
        }
}