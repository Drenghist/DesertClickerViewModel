package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.determineDessertToShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    //2-- Defino resetDessert
    fun resetDessert(){
        _uiState.value = DessertUiState()
    }

    fun dessertClick(){

        val dessertToShow = determineDessertToShow(desserts, dessertsSold)
        // Actualizamos el beneficio
        _uiState.update { currentState ->
            currentState.copy(
                // ¿Valdría también?
                // revenue = _uiState.value.revenue.plus(_uiState.value.currentDessertPrice),
                revenue = currentState.revenue.plus(currentState.currentDessertPrice),
                dessertsSold = currentState.dessertsSold.inc(),
                currentDessertImageId = dessertToShow.imageId,
                currentDessertPrice = dessertToShow.price,
            )
        }
    }


    //1--Decido empezar por el init
    init {
        resetDessert()
    }
}