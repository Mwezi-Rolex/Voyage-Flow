package com.example.matatu_navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.ui.graphics.Color // <-- ADD THIS IMPORT STATEMENT


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FareEstimatorScreen(
    start: String,
    end: String,
    onBack: () -> Unit
) {
    var estimatedFare by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(start, end) {
        isLoading = true
        error = null
        try {
            if (start.isBlank() || end.isBlank()) {
                throw IllegalArgumentException("Start and destination cannot be empty")
            }

            // pretend calculation
            estimatedFare = calculateFare(start, end)

            if (estimatedFare == null || estimatedFare!! <= 0) {
                throw Exception("Could not compute fare")
            }

        } catch (e: Exception) {
            error = e.localizedMessage ?: "Unexpected error occurred"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fare Estimator") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Text("From: $start", style = MaterialTheme.typography.bodyLarge)
            Text("To: $end", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(24.dp))

            when {
                isLoading -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {
                    ErrorCard(
                        message = error ?: "Something went wrong",
                        onRetry = {
                            // retrigger calculation
                            isLoading = true
                            error = null
                            estimatedFare = null

                            // manually re-run logic
                            try {
                                if (start.isBlank() || end.isBlank()) {
                                    throw IllegalArgumentException("Start and destination cannot be empty")
                                }
                                estimatedFare = calculateFare(start, end)
                                if (estimatedFare == null || estimatedFare!! <= 0) {
                                    throw Exception("Unable to compute fare")
                                }
                            } catch (e: Exception) {
                                error = e.localizedMessage
                            } finally {
                                isLoading = false
                            }
                        }
                    )
                }

                estimatedFare != null -> {
                    Text(
                        text = "Estimated Fare: KES ${estimatedFare!!.toInt()}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}


@Composable
fun ErrorCard(message: String, onRetry: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE5E5)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Error", style = MaterialTheme.typography.titleMedium, color = Color.Red)
            Spacer(Modifier.height(6.dp))
            Text(message, color = Color.Red)

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Retry")
            }
        }
    }
}



private fun calculateFare(start: String, end: String): Double {
    // Replace with real calculation
    val fare = start.length + end.length * 10.0
    if (fare < 10.0) return 10.0
    else if (fare < 200 && fare > 10.0) return 200.0
    else if (fare > 300 ) return 200.0
    return fare
}


