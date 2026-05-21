package com.example.ontop_challenge.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.ontop_challenge.data.repository.PokemonRepository
import com.example.ontop_challenge.domain.model.Pokemon
import com.example.ontop_challenge.domain.model.toPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokemonListUseCase(private val repository: PokemonRepository) {
    operator fun invoke(): Flow<PagingData<Pokemon>> =
        repository.getPokemonList()
            .map { pagingData ->
                pagingData.map { it.toPokemon() }
            }
}
