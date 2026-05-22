package com.example.ontop_challenge.data.datasource

class FakePokemonApiService : PokemonApiService {
    var pokemonListResponse: PokeListResponse = PokeListResponse(emptyList())
    var pokemonDetailResponse: PokemonDetailResponse? = null
    var shouldThrowException = false

    override suspend fun getPokemonList(offset: Int, limit: Int): PokeListResponse {
        if (shouldThrowException) throw Exception("Network error")
        return pokemonListResponse
    }

    override suspend fun getPokemonDetails(name: String): PokemonDetailResponse {
        if (shouldThrowException) throw Exception("Network error")
        return pokemonDetailResponse ?: throw Exception("Not found")
    }
}
