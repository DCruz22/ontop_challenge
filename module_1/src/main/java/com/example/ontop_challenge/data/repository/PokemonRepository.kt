package com.example.ontop_challenge.data.repository

import com.example.ontop_challenge.data.datasource.PokeDetailResponse
import com.example.ontop_challenge.data.datasource.PokemonResult
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<List<PokemonResult>>
    fun getPokemonDetails(name: String): Flow<PokeDetailResponse>
}