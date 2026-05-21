package com.example.ontop_challenge.data.datasource

import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(): PokeListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetailResponse
}
