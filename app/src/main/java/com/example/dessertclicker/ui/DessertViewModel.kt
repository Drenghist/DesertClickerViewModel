package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.determineDessertToShow
import com.example.dessertclicker.model.Dessert
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

    //6 -- Muevo aquí la función del click
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

    fun determineDessertToShow(desserts: List<Dessert>,dessertsSold: Int): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertToShow
    }

    //1--Decido empezar por el init
    init {
        resetDessert()
    }
}