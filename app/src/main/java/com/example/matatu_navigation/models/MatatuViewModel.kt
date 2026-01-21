package com.example.matatu_navigation.models

import android.util.Log
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MatatuViewModel(application: Application) : AndroidViewModel(application) {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // StateFlows to expose data
    private val _popularRoutes = MutableStateFlow<List<PopularRoute>>(emptyList())
    val popularRoutes: StateFlow<List<PopularRoute>> = _popularRoutes.asStateFlow()

    private val _userProfile = MutableStateFlow<Map<String, Any>>(emptyMap())
    val userProfile: StateFlow<Map<String, Any>> = _userProfile.asStateFlow()

    init {
        loadPopularRoutes()
        loadUserProfile()
    }

    // In app/src/main/java/com/example/matatu_navigation/models/MatatuViewModel.kt

//... inside the MatatuViewModel class

    private fun loadPopularRoutes() {
        viewModelScope.launch {
            try {
                val result = firestore.collection("popular_routes").get().await()
                if (result.isEmpty) {
                    Log.w("MatatuViewModel", "No documents found in popular_routes collection")
                }

                val routes = result.documents.mapNotNull { doc ->
                    try {
                        val id = doc.id
                        val name = doc.getString("name") ?: "Unnamed Route"
                        val routeNumber = doc.getString("routeNumber") ?: "N/A"
                        val startStage = doc.getString("startStage") ?: ""
                        val endStage = doc.getString("endStage") ?: ""
                        val estimatedFare = when (val fare = doc.get("estimatedFare")) {
                            is Number -> fare.toInt()
                            else -> {
                                Log.w("MatatuViewModel", "estimatedFare missing or wrong type for ${doc.id}")
                                0
                            }
                        }

                        val stageMaps = when (val stages = doc["stages"]) {
                            is List<*> -> stages.filterIsInstance<Map<String, Any>>()
                            else -> {
                                Log.w("MatatuViewModel", "stages missing or wrong type for ${doc.id}")
                                emptyList()
                            }
                        }

                        val stages = stageMaps.map { m ->
                            Stage(
                                id = m["id"] as? String ?: "",
                                name = m["name"] as? String ?: "",
                                latitude = (m["latitude"] as? Number)?.toDouble() ?: 0.0,
                                longitude = (m["longitude"] as? Number)?.toDouble() ?: 0.0
                            )
                        }

                        PopularRoute(
                            id = id,
                            name = name,
                            routeNumber = routeNumber,
                            startStage = startStage,
                            endStage = endStage,
                            estimatedFare = estimatedFare,
                            stages = stages
                        )
                    } catch (e: Exception) {
                        Log.e("MatatuViewModel", "Error parsing document ${doc.id}", e)
                        null
                    }
                }

                _popularRoutes.value = routes

            } catch (e: Exception) {
                Log.e("MatatuViewModel", "Failed to load popular routes", e)
                _popularRoutes.value = emptyList()
            }
        }
    }



    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val doc = firestore.collection("users").document("demo_user").get().await()
                _userProfile.value = doc.data ?: emptyMap()
            } catch (e: Exception) {
                // Handle the exception
                _userProfile.value = emptyMap()
            }
        }
    }
}
