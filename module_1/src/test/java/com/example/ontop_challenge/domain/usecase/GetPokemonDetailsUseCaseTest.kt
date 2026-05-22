package com.example.ontop_challenge.domain.usecase

import app.cash.turbine.test
import com.example.ontop_challenge.data.datasource.PokemonDetailResponse
import com.example.ontop_challenge.data.datasource.Sprites
import com.example.ontop_challenge.data.repository.FakePokemonRepository
import com.example.ontop_challenge.domain.model.toPokemonDetail
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPokemonDetailsUseCaseTest {

    private lateinit var fakeRepository: FakePokemonRepository
    private lateinit var useCase: GetPokemonDetailsUseCase

    @Before
    fun setup() {
        fakeRepository = FakePokemonRepository()
        useCase = GetPokemonDetailsUseCase(fakeRepository)
    }

    @Test
    fun `invoke returns mapped pokemon details`() = runTest {
        val detailResponse = PokemonDetailResponse(
            id = 1,
            name = "bulbasaur",
            sprites = Sprites("url"),
            height = 7,
            weight = 69
        )
        fakeRepository.pokemonDetailsResponse = detailResponse
        val expectedDetail = detailResponse.toPokemonDetail()

        useCase("bulbasaur").test {
            assertEquals(com.example.ontop_challenge.domain.model.UiState.Loading, awaitItem())
            val readyState = awaitItem() as com.example.ontop_challenge.domain.model.UiState.Ready
            assertEquals(expectedDetail, readyState.data)
            awaitComplete()
        }
    }

    @Test
    fun `invoke returns error state when repository throws`() = runTest {
        fakeRepository.shouldThrowException = true

        useCase("bulbasaur").test {
            assertEquals(com.example.ontop_challenge.domain.model.UiState.Loading, awaitItem())
            val errorState = awaitItem() as com.example.ontop_challenge.domain.model.UiState.Error
            assertEquals("Error", errorState.exception.message)
            awaitComplete()
        }
    }
}
