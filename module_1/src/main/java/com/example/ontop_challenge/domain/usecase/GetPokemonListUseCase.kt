package com.example.ontop_challenge.domain.usecase

import com.example.ontop_challenge.data.repository.PokemonRepository
import com.example.ontop_challenge.domain.model.Pokemon
import com.example.ontop_challenge.domain.model.UiState
import com.example.ontop_challenge.domain.model.toPokemon
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.timeout
import kotlin.time.Duration.Companion.seconds

class GetPokemonListUseCase(private val repository: PokemonRepository) {
    @OptIn(FlowPreview::class)
    operator fun invoke(): Flow<UiState<List<Pokemon>>> =
        repository.getPokemonList()
            .timeout(10.seconds)
            .map { results ->
                if (results.isEmpty()) {
                    UiState.Empty
                } else {
                    val domainList = results.map { it.toPokemon() }
                    UiState.Ready(domainList)
                }
            }
            .onStart { emit(UiState.Loading) }
            .catch { exception ->
                emit(UiState.Error(exception))
            }
}
