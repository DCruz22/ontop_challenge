package com.example.ontop_challenge.domain.usecase

import com.example.ontop_challenge.data.repository.PokemonRepository
import com.example.ontop_challenge.domain.model.PokemonDetail
import com.example.ontop_challenge.domain.model.UiState
import com.example.ontop_challenge.domain.model.toPokemonDetail
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.timeout
import kotlin.time.Duration.Companion.seconds

class GetPokemonDetailsUseCase(private val repository: PokemonRepository) {
    @OptIn(FlowPreview::class)
    operator fun invoke(name: String): Flow<UiState<PokemonDetail>> =
        repository.getPokemonDetails(name)
            .timeout(10.seconds)
            .map { response ->
                val detail = response.toPokemonDetail()
                UiState.Ready(detail) as UiState<PokemonDetail>
            }
            .onStart { emit(UiState.Loading) }
            .catch { exception ->
                emit(UiState.Error(exception))
            }
}
