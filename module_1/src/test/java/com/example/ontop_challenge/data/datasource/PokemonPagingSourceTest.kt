package com.example.ontop_challenge.data.datasource

import androidx.paging.PagingSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PokemonPagingSourceTest {

    private lateinit var fakePokemonApiService: FakePokemonApiService
    private lateinit var pagingSource: PokemonPagingSource

    @Before
    fun setup() {
        fakePokemonApiService = FakePokemonApiService()
        pagingSource = PokemonPagingSource(fakePokemonApiService)
    }

    @Test
    fun `load returns success when service returns data`() = runTest {
        val pokemonList = listOf(
            PokemonResult("bulbasaur", "url1"),
            PokemonResult("ivysaur", "url2")
        )
        fakePokemonApiService.pokemonListResponse = PokeListResponse(pokemonList)

        val expectedResult = PagingSource.LoadResult.Page(
            data = pokemonList,
            prevKey = null,
            nextKey = 2
        )

        val actualResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `load returns error when service throws exception`() = runTest {
        fakePokemonApiService.shouldThrowException = true

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
    }
}
