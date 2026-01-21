package com.example.matatu_navigation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ensure Firebase is initialized (if google-services.json is present this is often automatic,
        // but explicit init is safe).
        FirebaseApp.initializeApp(this)

        setContent {
            val primaryColor = Color(0xFFF59E0B)
            val darkGray = Color(0xFF374151)
            val lightBackground = Color(0xFFF9FAFB)

            MaterialTheme(
                colorScheme = androidx.compose.material3.lightColorScheme(
                    primary = primaryColor,
                    onPrimary = Color.White,
                    background = lightBackground,
                    surface = Color.White,
                    onSurface = darkGray
                )
            ) {
                MatatuNavigatorApp()
            }
        }
    }
}

