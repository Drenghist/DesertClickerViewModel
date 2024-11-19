package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DessertViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    //2-- Defino resetDessert
    fun resetDessert(){
        _uiState.value = DessertUiState()
    }

    //1--Decido empezar por el init
    init {
        resetDessert()
    }
}