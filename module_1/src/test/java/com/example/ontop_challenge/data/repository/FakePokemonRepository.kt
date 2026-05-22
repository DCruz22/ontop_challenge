package com.example.ontop_challenge.data.repository

import androidx.paging.PagingData
import com.example.ontop_challenge.data.datasource.PokemonDetailResponse
import com.example.ontop_challenge.data.datasource.PokemonResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class FakePokemonRepository : PokemonRepository {
    
    val pokemonListFlow = MutableStateFlow<PagingData<PokemonResult>>(PagingData.empty())
    var pokemonDetailsResponse: PokemonDetailResponse? = null
    var shouldThrowException = false

    override fun getPokemonList(): Flow<PagingData<PokemonResult>> {
        if (shouldThrowException) return flow { throw Exception("Error") }
        return pokemonListFlow
    }

    override fun getPokemonDetails(name: String): Flow<PokemonDetailResponse> = flow {
        if (shouldThrowException) throw Exception("Error")
        emit(pokemonDetailsResponse ?: throw Exception("Not found"))
    }
}
