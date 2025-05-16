package com.example.projectse104.domain.repository

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User

typealias UserResponse = Response<User>
typealias UserListResponse = Response<List<User>>
typealias AddUserResponse = Response<Unit>
typealias UpdateUserResponse = Response<Unit>
typealias DeleteUserResponse = Response<Unit>


interface UserRepository {

    suspend fun getUser(userId: String): UserResponse

    suspend fun insertUser(user: User): AddUserResponse

    suspend fun updateUser(userUpdate: Map<String, String>): UpdateUserResponse

    suspend fun deleteUser(userId: String): DeleteUserResponse
}