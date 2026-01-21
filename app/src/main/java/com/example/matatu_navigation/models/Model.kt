package com.example.matatu_navigation.models

data class Stage(
    val id: String = "",
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

data class FareEstimate(
    val startStage: String = "",
    val endStage: String = "",
    val routeNumber: String = "N/A",
    val minFare: Int = 100,
    val maxFare: Int = 200,
)

data class UserProfile(
    val userId: String = "",
    val email: String? = null,
    val recentSearches: List<FareEstimate> = emptyList()
)

data class PopularRoute(
    val id: String = "",
    val routeNumber: String = "N/A",
    val name: String = "",
    val startStage: String = "",
    val endStage: String = "",
    val estimatedFare: Int = 0,
    val stages: List<Stage> = emptyList()
)