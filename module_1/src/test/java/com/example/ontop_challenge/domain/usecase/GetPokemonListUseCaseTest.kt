package com.example.ontop_challenge.domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.ontop_challenge.data.datasource.PokemonResult
import com.example.ontop_challenge.data.repository.FakePokemonRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class GetPokemonListUseCaseTest {

    private lateinit var fakeRepository: FakePokemonRepository
    private lateinit var useCase: GetPokemonListUseCase

    @Before
    fun setup() {
        fakeRepository = FakePokemonRepository()
        useCase = GetPokemonListUseCase(fakeRepository)
    }

    @Test
    fun `invoke returns paging data flow`() = runTest {
        val pagingData = PagingData.from(listOf(PokemonResult("bulbasaur", "url")))
        fakeRepository.pokemonListFlow.value = pagingData
        
        useCase().test {
            assertNotNull(awaitItem())
        }
    }

    @Test
    fun `invoke propagates error from repository`() = runTest {
        fakeRepository.shouldThrowException = true
        
        useCase().test {
            val error = awaitError()
            assertEquals("Error", error.message)
        }
    }
}
