package com.example.ontop_challenge.data.repository

import app.cash.turbine.test
import com.example.ontop_challenge.data.datasource.FakePokemonApiService
import com.example.ontop_challenge.data.datasource.PokemonDetailResponse
import com.example.ontop_challenge.data.datasource.Sprites
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class PokemonRepositoryImplTest {

    private lateinit var fakePokeApiService: FakePokemonApiService
    private lateinit var repository: PokemonRepositoryImpl

    @Before
    fun setup() {
        fakePokeApiService = FakePokemonApiService()
        repository = PokemonRepositoryImpl(fakePokeApiService)
    }

    @Test
    fun `getPokemonList returns a flow of paging data`() = runTest {
        val result = repository.getPokemonList().first()
        assertNotNull(result)
    }

    @Test
    fun `getPokemonDetails returns expected details`() = runTest {
        val expectedDetails = PokemonDetailResponse(
            id = 1,
            name = "bulbasaur",
            sprites = Sprites("url"),
            height = 7,
            weight = 69
        )
        fakePokeApiService.pokemonDetailResponse = expectedDetails

        repository.getPokemonDetails("bulbasaur").test {
            assertEquals(expectedDetails, awaitItem())
            awaitComplete()
        }
    }
}
