package com.example.ontop_challenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontop_challenge.domain.model.PokemonDetail
import com.example.ontop_challenge.domain.model.UiState
import com.example.ontop_challenge.domain.usecase.GetPokemonDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class PokemonDetailsViewModel(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val pokemonName = MutableStateFlow<String?>(null)
    private val reloadTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<UiState<PokemonDetail>> = combine(
        pokemonName.filterNotNull(),
        reloadTrigger.onStart { emit(Unit) }
    ) { name, _ -> name }
        .flatMapLatest { name ->
            getPokemonDetailsUseCase(name)
        }
        .flowOn(dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun reloadPokemonDetails() {
        reloadTrigger.tryEmit(Unit)
    }

    fun loadPokemon(name: String) {
        pokemonName.value = name
    }
}
