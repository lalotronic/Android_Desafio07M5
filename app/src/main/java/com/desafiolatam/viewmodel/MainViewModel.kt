package com.desafiolatam.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val upperCounterMutableStateFlow: MutableStateFlow<Int> = MutableStateFlow(10)
    private val lowerCounterMutableStateFlow: MutableStateFlow<Int> = MutableStateFlow(10)

    val upperCounterStateFlow: StateFlow<Int> = upperCounterMutableStateFlow.asStateFlow()
    val lowerCounterStateFlow: StateFlow<Int> = lowerCounterMutableStateFlow.asStateFlow()


    fun increaseUpper() {
        // Obtiene el valor actual del contador superior
        val currentValue = upperCounterMutableStateFlow.value
        // Incrementa el valor
        upperCounterMutableStateFlow.value = currentValue + 1
    }
    fun decrementUpper() {
        // Obtiene el valor actual del contador superior
        val currentValue = upperCounterMutableStateFlow.value
        // Incrementa el valor
        upperCounterMutableStateFlow.value = currentValue - 1
    }


    fun increaseLower() {
        val currentValue = lowerCounterMutableStateFlow.value
        lowerCounterMutableStateFlow.value = currentValue + 1
    }
    fun decrementLower() {
        val currentValue = lowerCounterMutableStateFlow.value
        lowerCounterMutableStateFlow.value = currentValue - 1
    }

    fun resetScores() {
        upperCounterMutableStateFlow.value = 10 // O 0, según lo que necesites
        lowerCounterMutableStateFlow.value = 10 // O 0
    }

    fun isGameFinished(): Boolean {
        return upperCounterMutableStateFlow.value >= 20 || lowerCounterMutableStateFlow.value >= 20 // Cambia 20 por el valor que desees
    }


}