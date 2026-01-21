package com.example.matatu_navigation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
@Composable
fun TripProgressScreen(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)) {
        Text("Trip Progress", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        LinearProgressIndicator(progress = 0.5f, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        val steps = listOf(
            "Driver assigned - 08:00",
            "Driver en route - 08:05",
            "Arrived at pickup - 08:20",
            "Trip started - 08:25"
        )
        steps.forEach { step ->
            Text(step, modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressPreview() {
    TripProgressScreen(navController = rememberNavController())
}
