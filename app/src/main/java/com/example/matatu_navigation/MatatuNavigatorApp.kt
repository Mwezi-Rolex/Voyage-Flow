package com.example.matatu_navigation

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatatuNavigatorApp(
    viewModel: com.example.matatu_navigation.models.MatatuViewModel =
        viewModel()
) {
    val navController = rememberNavController()
    val popularRoutes by viewModel.popularRoutes.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        /* ---------------- HOME ---------------- */
        composable("home") {
            HomeScreen(
                popularRoutes = popularRoutes,
                onNavigateToFareEstimator = { start, end ->
                    val s = Uri.encode(start)
                    val e = Uri.encode(end)
                    navController.navigate("fare/$s/$e")
                },
                onOpenRouteDetails = { routeId ->
                    if (routeId != null)
                        navController.navigate("routeDetails/${Uri.encode(routeId)}")
                    else
                        navController.navigate("routeDetails")
                },
                onOpenMap = { origin, destination ->
                    navController.navigate(
                        "map/${Uri.encode(origin)}/${Uri.encode(destination)}"
                    )
                }
            )
        }

        /* ---------------- ROUTE DETAILS ---------------- */
        composable(
            "routeDetails/{routeId}",
            arguments = listOf(
                navArgument("routeId") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            val id = entry.arguments?.getString("routeId") ?: ""

            RouteDetailsScreen(
                routeId = id.ifBlank { null },
                onBack = { navController.popBackStack() },
                onStartLiveTrip = {
                    navController.navigate("tripMap")
                }
            )
        }

        /* ---------------- MAP SCREEN ---------------- */
        composable(
            "map/{origin}/{destination}",
            arguments = listOf(
                navArgument("origin") { type = NavType.StringType },
                navArgument("destination") { type = NavType.StringType }
            )
        ) { entry ->
            val origin = Uri.decode(entry.arguments?.getString("origin") ?: "")
            val dest = Uri.decode(entry.arguments?.getString("destination") ?: "")

            MapScreen(
                origin = origin,
                destination = dest,
                onBack = { navController.popBackStack() }
            )
        }

        /* ---------------- FARE ESTIMATOR ---------------- */
        composable(
            "fare/{start}/{end}",
            arguments = listOf(
                navArgument("start") { type = NavType.StringType },
                navArgument("end") { type = NavType.StringType }
            )
        ) { entry ->
            val start = Uri.decode(entry.arguments?.getString("start") ?: "")
            val end = Uri.decode(entry.arguments?.getString("end") ?: "")

            FareEstimatorScreen(
                start = start,
                end = end,
                onBack = { navController.popBackStack() }
            )
        }

        /* ---------------- TRIP MAP ---------------- */
        composable("tripMap") {
            com.example.matatu_navigation.MapScreen(
                origin = "Your Location",
                destination = "Destination",
                onBack = { navController.popBackStack() }
            )
        }
    }
}


