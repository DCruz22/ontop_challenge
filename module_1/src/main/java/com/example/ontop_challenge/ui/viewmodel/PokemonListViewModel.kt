package com.example.ontop_challenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ontop_challenge.domain.model.Pokemon
import com.example.ontop_challenge.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val dispatchers: Dispatchers,
) : ViewModel() {

    val pokemonPagingData: Flow<PagingData<Pokemon>> = getPokemonListUseCase()
        .flowOn(dispatchers.IO)
        .cachedIn(viewModelScope)

}
