package com.example.projectse104

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HitchHikingApp : Application(){override fun onCreate() {
    super.onCreate()
    FirebaseApp.initializeApp(this)
}}