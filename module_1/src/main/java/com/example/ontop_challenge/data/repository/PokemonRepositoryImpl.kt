package com.example.ontop_challenge.data.repository

import com.example.ontop_challenge.data.datasource.PokeApiService
import com.example.ontop_challenge.data.datasource.PokemonDetailResponse
import com.example.ontop_challenge.data.datasource.PokemonResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(private val pokeApiService: PokeApiService) : PokemonRepository {
    override fun getPokemonList(): Flow<List<PokemonResult>> = flow {
        emit(pokeApiService.getPokemonList().results)
    }

    override fun getPokemonDetails(name: String): Flow<PokemonDetailResponse> = flow {
        emit(pokeApiService.getPokemonDetails(name))
    }
}
