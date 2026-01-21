package com.example.matatu_navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.lazy.items
import com.google.android.gms.maps.model.LatLng // Import LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.example.matatu_navigation.models.Stage
import com.example.matatu_navigation.models.PopularRoute
import com.google.firebase.firestore.DocumentSnapshot


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.RadioButtonUnchecked

import kotlinx.coroutines.tasks.await

// --- Renamed sealed class to avoid conflict with DirectionsRepository.RouteResult ---
sealed class RouteResultFirestore {
    object Loading : RouteResultFirestore()
    data class Success(val route: PopularRoute) : RouteResultFirestore()
    data class Error(val message: String) : RouteResultFirestore()
}

// Helper function to map Firestore snapshot to PopularRoute
fun mapSnapToRoute(snap: DocumentSnapshot): PopularRoute {
    val stages = (snap["stages"] as? List<Map<String, Any>>)?.map { m ->
        Stage(
            id = m["id"] as? String ?: "",
            name = m["name"] as? String ?: "",
            latitude = (m["latitude"] as? Number)?.toDouble() ?: 0.0,
            longitude = (m["longitude"] as? Number)?.toDouble() ?: 0.0
        )
    } ?: emptyList()

    // Handle estimatedFare as Number or String
    val estimatedFare = when (val fare = snap["estimatedFare"]) {
        is Number -> fare.toInt()
        is String -> fare.toIntOrNull() ?: 0
        else -> 0
    }

    return PopularRoute(
        id = snap.id,
        routeNumber = snap.getString("routeNumber") ?: "N/A",
        name = snap.getString("name") ?: snap.getString("routeNumber") ?: "N/A",
        startStage = snap.getString("startStage") ?: "",
        endStage = snap.getString("endStage") ?: "",
        estimatedFare = estimatedFare,
        stages = stages
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailsScreen(
    routeId: String? = null,
    onBack: () -> Unit,
    onStartLiveTrip: () -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()
    var routeState by remember { mutableStateOf<RouteResultFirestore>(RouteResultFirestore.Loading) }

    // Fetch route once if routeId is provided
    LaunchedEffect(routeId) {
        if (routeId == null) {
            routeState = RouteResultFirestore.Error("No route selected")
            return@LaunchedEffect
        }

        try {
            val snap = firestore.collection("popular_routes").document(routeId).get().await()
            routeState = if (snap.exists()) {
                RouteResultFirestore.Success(mapSnapToRoute(snap))
            } else {
                RouteResultFirestore.Error("Route not found")
            }
        } catch (e: Exception) {
            routeState = RouteResultFirestore.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        (routeState as? RouteResultFirestore.Success)?.route?.name ?: "Route Details"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (val state = routeState) {
                is RouteResultFirestore.Loading -> {
                    CircularProgressIndicator()
                }

                is RouteResultFirestore.Error -> {
                    Text("Error: ${state.message}", color = Color.Red)
                }

                is RouteResultFirestore.Success -> {
                    val r = state.route
                    Text("Route ${r.routeNumber}", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Estimated Fare: Ksh ${r.estimatedFare}")
                    Spacer(Modifier.height(8.dp))

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(r.stages) { stage ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.RadioButtonUnchecked, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text(stage.name)
                            }
                            Divider()
                        }
                    }

                    Button(
                        onClick = onStartLiveTrip,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Start Live Trip (Placeholder)")
                    }
                }
            }
        }
    }
}
