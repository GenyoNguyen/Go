package com.example.projectse104.data.repository

import com.example.projectse104.core.ID_FIELD
import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.model.toUser
import com.example.projectse104.domain.repository.DeleteUserResponse
import com.example.projectse104.domain.repository.UpdateUserResponse
import com.example.projectse104.domain.repository.UserRepository
import com.example.projectse104.domain.repository.UserResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val usersRef: CollectionReference
) : UserRepository {

    override suspend fun getUser(userId: String): UserResponse = try {
        val userSnapshot =
            usersRef.document(userId).get().await()
        Response.Success(userSnapshot.toUser())
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun addUser(user: User) = try {
        usersRef.add(user)
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun updateUser(userUpdate: Map<String, Any>): UpdateUserResponse = try {
        val userId = userUpdate.getValue(ID_FIELD) as String
        usersRef.document(userId).update(userUpdate).await()
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun deleteUser(userId: String): DeleteUserResponse = try {
        usersRef.document(userId).delete().await()
        Response.Success(Unit)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}