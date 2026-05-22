package com.example.ontop_challenge.ui.viewmodel

import app.cash.turbine.test
import com.example.ontop_challenge.data.datasource.PokemonDetailResponse
import com.example.ontop_challenge.data.datasource.Sprites
import com.example.ontop_challenge.data.repository.FakePokemonRepository
import com.example.ontop_challenge.domain.model.UiState
import com.example.ontop_challenge.domain.model.toPokemonDetail
import com.example.ontop_challenge.domain.usecase.GetPokemonDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailsViewModelTest {

    private lateinit var fakeRepository: FakePokemonRepository
    private lateinit var useCase: GetPokemonDetailsUseCase
    private lateinit var viewModel: PokemonDetailsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakePokemonRepository()
        useCase = GetPokemonDetailsUseCase(fakeRepository)
        viewModel = PokemonDetailsViewModel(useCase, Dispatchers)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState initially emits Loading`() = runTest {
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
        }
    }

    @Test
    fun `loadPokemon updates uiState to Ready`() = runTest {
        val detailResponse = PokemonDetailResponse(
            id = 1,
            name = "bulbasaur",
            sprites = Sprites("url"),
            height = 7,
            weight = 69
        )
        fakeRepository.pokemonDetailsResponse = detailResponse
        val expectedDetail = detailResponse.toPokemonDetail()

        viewModel.loadPokemon("bulbasaur")
        
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            val ready = awaitItem() as UiState.Ready
            assertEquals(expectedDetail, ready.data)
        }
    }

    @Test
    fun `reloadPokemonDetails restarts the flow`() = runTest {
        val detailResponse = PokemonDetailResponse(
            id = 1,
            name = "bulbasaur",
            sprites = Sprites("url"),
            height = 7,
            weight = 69
        )
        fakeRepository.pokemonDetailsResponse = detailResponse
        val expectedDetail = detailResponse.toPokemonDetail()

        viewModel.loadPokemon("bulbasaur")
        
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            val firstReady = awaitItem() as UiState.Ready
            assertEquals(expectedDetail, firstReady.data)
            
            viewModel.reloadPokemonDetails()
            
            assertEquals(UiState.Loading, awaitItem())
            val secondReady = awaitItem() as UiState.Ready
            assertEquals(expectedDetail, secondReady.data)
        }
    }
}
