package com.example.ontop_challenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontop_challenge.data.datasource.PokeDetailResponse
import com.example.ontop_challenge.domain.model.UiState
import com.example.ontop_challenge.domain.usecase.GetPokemonDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class PokemonDetailsViewModel(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val pokemonName = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<UiState<PokeDetailResponse>> = pokemonName
        .filterNotNull()
        .flatMapLatest { name ->
            getPokemonDetailsUseCase(name)
        }
        .flowOn(dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun getPokemonDetails() {
        pokemonName.value?.let { name ->
            pokemonName.value = null
            pokemonName.value = name
        }
    }
}
