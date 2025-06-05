package com.example.projectse104.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class DataStoreSessionManager(private val context: Context) {

    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_NAME = stringPreferencesKey("user_name")
    }

    suspend fun saveUserSession(userId: String, email: String, userName: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[USER_ID] = userId
            preferences[USER_EMAIL] = email
            preferences[USER_NAME] = userName
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val userId: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_ID]
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_EMAIL]
    }

    val userName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME]
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
            Log.d("DataStoreSessionManager", "Session cleared")
        }
    }

    suspend fun updateUserInfo(userName: String? = null, email: String? = null) {
        context.dataStore.edit { preferences ->
            userName?.let { preferences[USER_NAME] = it }
            email?.let { preferences[USER_EMAIL] = it }
        }
    }

    val userInfo: Flow<UserSessionInfo> = context.dataStore.data.map { preferences ->
        UserSessionInfo(
            isLoggedIn = preferences[IS_LOGGED_IN] ?: false,
            userId = preferences[USER_ID],
            email = preferences[USER_EMAIL],
            userName = preferences[USER_NAME]
        )
    }
}

data class UserSessionInfo(
    val isLoggedIn: Boolean,
    val userId: String?,
    val email: String?,
    val userName: String?
)