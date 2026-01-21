package com.example.matatu_navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matatu_navigation.models.PopularRoute // ✅ Import PopularRoute
import androidx.compose.ui.res.stringResource // Import stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    popularRoutes: List<PopularRoute>,
    onNavigateToFareEstimator: (String, String) -> Unit,
    onOpenRouteDetails: (String?) -> Unit,
    onOpenMap: (String, String) -> Unit
) {
    var startLocation by remember { mutableStateOf("") }
    var endLocation by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = startLocation,
                onValueChange = { startLocation = it },
                label = { Text(stringResource(R.string.start_location)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = endLocation,
                onValueChange = { endLocation = it },
                label = { Text(stringResource(R.string.destination)) },
                modifier = Modifier.fillMaxWidth()
            )

            Row( // Add a Row for the two buttons
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribute space evenly
            ) {
                Button(
                    onClick = {
                        if (startLocation.isNotBlank() && endLocation.isNotBlank())
                            onNavigateToFareEstimator(startLocation, endLocation)
                    },
                    modifier = Modifier.weight(1f) // Make button take available space
                ) {
                    Text(stringResource(R.string.estimate_fare))
                }
                Spacer(modifier = Modifier.width(8.dp)) // Add some space between buttons
                Button(
                    onClick = {
                        if (startLocation.isNotBlank() && endLocation.isNotBlank())
                            onOpenMap(startLocation, endLocation)
                    },
                    modifier = Modifier.weight(1f) // Make button take available space
                ) {
                    Text(stringResource(R.string.show_map))
                }
            }

            Text(
                "Popular Routes", // This string is not in strings.xml, will leave it for now.
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn {
                items(popularRoutes) { route ->
                    Card(
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .fillMaxWidth()
                            .clickable { onOpenRouteDetails(route.id) } // Use ID for navigation
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(text = route.name)
                        }
                    }
                }
            }
        }
    }
}
