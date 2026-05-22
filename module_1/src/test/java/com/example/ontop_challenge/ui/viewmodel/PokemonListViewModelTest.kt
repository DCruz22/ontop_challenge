package com.example.ontop_challenge.ui.viewmodel

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.ontop_challenge.data.datasource.PokemonResult
import com.example.ontop_challenge.data.repository.FakePokemonRepository
import com.example.ontop_challenge.domain.usecase.GetPokemonListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    private lateinit var fakeRepository: FakePokemonRepository
    private lateinit var useCase: GetPokemonListUseCase
    private lateinit var viewModel: PokemonListViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakePokemonRepository()
        useCase = GetPokemonListUseCase(fakeRepository)
        viewModel = PokemonListViewModel(useCase, Dispatchers)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `pokemonPagingData emits paging data`() = runTest {
        val pagingData = PagingData.from(listOf(PokemonResult("bulbasaur", "url")))
        fakeRepository.pokemonListFlow.value = pagingData

        viewModel.pokemonPagingData.test {
            assertNotNull(awaitItem())
        }
    }
}
