package com.example.projectse104.domain.use_case.user

import com.example.projectse104.core.Response
import com.example.projectse104.domain.model.User
import com.example.projectse104.domain.repository.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(user: User): Flow<Response<Unit>> = flow {
        emit(Response.Loading)
        try {
            // Hash the password
            val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())

            // Create a user object with the hashed password
            val userToInsert = user.copy(
                password = hashedPassword
            )

            // Insert the user into the database
            val insertResult = repository.insertUser(userToInsert)
            when (insertResult) {
                is Response.Success -> emit(Response.Success(Unit))
                is Response.Failure -> emit(Response.Failure(insertResult.e))
                else -> emit(Response.Failure(Exception("Unexpected error during user insertion")))
            }
        } catch (e: Exception) {
            when {
                e.message?.contains("User already registered") == true -> {
                    emit(Response.Failure(Exception("Email already exists")))
                }
                e.message?.contains("network") == true -> {
                    emit(Response.Failure(Exception("Network error")))
                }
                else -> emit(Response.Failure(Exception(e.message ?: "Unknown error")))
            }
        }
    }
}