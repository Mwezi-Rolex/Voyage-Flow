package com.example.matatu_navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel // Import viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    origin: String,
    destination: String,
    onBack: () -> Unit,
    mapViewModel: MapViewModel = viewModel() // Inject MapViewModel
) {
    val cameraPositionState = rememberCameraPositionState()

    var permissionGranted by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> permissionGranted = granted }

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // Fetch route once
    LaunchedEffect(origin, destination) {
        mapViewModel.getRoute(origin, destination)
    }

    val uiState by mapViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$origin → $destination") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            when (uiState) {
                is MapUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is MapUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${(uiState as MapUiState.Error).message}", color = MaterialTheme.colorScheme.error)
                    }
                }
                is MapUiState.Success -> {
                    val route = uiState as MapUiState.Success

                    GoogleMap(
                        modifier = Modifier.weight(1f),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(isMyLocationEnabled = permissionGranted)
                    ) {
                        Polyline(
                            points = route.polylinePoints,
                            color = MaterialTheme.colorScheme.primary,
                            width = 10f
                        )

                        // Move camera to start of route
                        LaunchedEffect(route.polylinePoints) {
                            if (route.polylinePoints.isNotEmpty()) {
                                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(route.polylinePoints.first(), 12f))
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Distance: ${route.distance}", style = MaterialTheme.typography.titleMedium)
                            Text("Duration: ${route.duration}", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}
