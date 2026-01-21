package com.example.matatu_navigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<MapUiState>(MapUiState.Loading)
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun getRoute(origin: String, destination: String) {
        viewModelScope.launch {
            _uiState.value = MapUiState.Loading
            try {
                val routeResult = DirectionsRepository.getRoute(origin, destination)
                _uiState.value = MapUiState.Success(
                    polylinePoints = routeResult.polylinePoints,
                    distance = routeResult.distanceText,
                    duration = routeResult.durationText
                )
            } catch (e: Exception) {
                _uiState.value = MapUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

sealed class MapUiState {
    object Loading : MapUiState()
    data class Success(
        val polylinePoints: List<LatLng>,
        val distance: String,
        val duration: String
    ) : MapUiState()

    data class Error(val message: String) : MapUiState()
}
