package com.example.ontop_challenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ontop_challenge.domain.model.Pokemon
import com.example.ontop_challenge.domain.model.UiState
import com.example.ontop_challenge.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    private val refreshSignal = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<UiState<List<Pokemon>>> = refreshSignal
        .flatMapLatest {
            getPokemonListUseCase()
        }
        .flowOn(dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        refreshSignal.tryEmit(Unit)
    }

}
