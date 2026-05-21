package com.example.ontop_challenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ontop_challenge.data.datasource.PokeApiService
import com.example.ontop_challenge.data.datasource.PokemonDetailResponse
import com.example.ontop_challenge.data.datasource.PokemonPagingSource
import com.example.ontop_challenge.data.datasource.PokemonResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(private val pokeApiService: PokeApiService) : PokemonRepository {
    override fun getPokemonList(): Flow<PagingData<PokemonResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokemonPagingSource(pokeApiService) }
        ).flow
    }

    override fun getPokemonDetails(name: String): Flow<PokemonDetailResponse> = flow {
        emit(pokeApiService.getPokemonDetails(name))
    }
}
