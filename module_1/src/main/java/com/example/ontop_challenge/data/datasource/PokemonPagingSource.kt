package com.example.ontop_challenge.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PokemonPagingSource(
    private val pokeApiService: PokeApiService
) : PagingSource<Int, PokemonResult>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        return try {
            val offset = params.key ?: 0
            val limit = params.loadSize
            val response = pokeApiService.getPokemonList(offset = offset, limit = limit)
            val results = response.results

            LoadResult.Page(
                data = results,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (results.isEmpty()) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
