package com.example.projectse104.data.repository

import com.example.projectse104.core.ID_FIELD
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.repository.DeleteUserResponse
import com.example.projectse104.domain.repository.UpdateUserResponse
import com.example.projectse104.domain.repository.UserRepository
import com.example.projectse104.domain.repository.UserResponse
import io.github.jan.supabase.postgrest.query.PostgrestQueryBuilder

class UserRepositoryImpl(
    private val usersRef: PostgrestQueryBuilder
) : UserRepository {

    override suspend fun getUser(userId: String): UserResponse = try {
        val userResponse =
            usersRef.select {
                filter {
                    User::id eq userId
                }
            }.decodeSingle<User>()
        Response.Success(userResponse)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun addUser(user: User) = try {
        usersRef.insert(user)
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun updateUser(userUpdate: Map<String, Any>): UpdateUserResponse = try {
        val userId = userUpdate.getValue(ID_FIELD) as String
        usersRef.update({
            for ((key, value) in userUpdate) {
                set(key, value)
            }
        }) {
            filter {
                User::id eq userId
            }
        }
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteUser(userId: String): DeleteUserResponse = try {
        usersRef.delete {
            filter {
                User::id eq userId
            }
        }
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}