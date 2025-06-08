package com.example.projectse104

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HitchHikingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            FirebaseApp.initializeApp(this)
            Log.d(
                "Firebase",
                "Firebase initialized successfully: ${FirebaseApp.getInstance().name}"
            )
        } catch (e: Exception) {
            Log.e("Firebase", "Failed to initialize Firebase: ${e.message}", e)
        }
    }
}
