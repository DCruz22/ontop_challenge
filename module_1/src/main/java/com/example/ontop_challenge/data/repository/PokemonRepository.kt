package com.example.ontop_challenge.data.repository

import androidx.paging.PagingData
import com.example.ontop_challenge.data.datasource.PokemonDetailResponse
import com.example.ontop_challenge.data.datasource.PokemonResult
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<PokemonResult>>
    fun getPokemonDetails(name: String): Flow<PokemonDetailResponse>
}