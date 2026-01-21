package com.example.matatu_navigation

import android.util.Log
import android.app.Application
import com.google.firebase.FirebaseApp

class MatatuApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val app = FirebaseApp.initializeApp(this)
        if (app != null) {
            Log.d("FirebaseTest", "Firebase initialized successfully")
        } else {
            Log.e("FirebaseTest", "Firebase initialization failed")
        }
    }

}
