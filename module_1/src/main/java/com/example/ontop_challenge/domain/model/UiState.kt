package com.example.ontop_challenge.domain.model

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Ready<T>(val data: T) : UiState<T>()
    data class Error(val exception: Throwable) : UiState<Nothing>()
}
