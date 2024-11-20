package com.example.dessertclicker.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.dessertclicker.R
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    //2 -- Defino resetDessert
    fun resetDessert(){
        _uiState.value = DessertUiState()
    }

    //6 -- Muevo aquí la función del click
    fun dessertClick(desserts: List<Dessert>, ){

        val dessertToShow = determineDessertToShow(desserts, _uiState.value.dessertsSold)
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
    //7 -- Muevo también la función que me determine el postre
   private fun determineDessertToShow(desserts: List<Dessert>,dessertsSold: Int): Dessert {
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

    /**
     * Share desserts sold information using ACTION_SEND intent
     */
    fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                intentContext.getString(R.string.share_text, dessertsSold, revenue)
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        try {
            ContextCompat.startActivity(intentContext, shareIntent, null)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                intentContext,
                intentContext.getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //1--Decido empezar por el init
    init {
        resetDessert()
    }
}