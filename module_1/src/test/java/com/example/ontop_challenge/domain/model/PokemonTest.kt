package com.example.ontop_challenge.domain.model

import com.example.ontop_challenge.data.datasource.PokemonResult
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonTest {

    @Test
    fun `toPokemon maps PokemonResult correctly`() {
        val result = PokemonResult(
            name = "bulbasaur",
            url = "https://pokeapi.co/api/v2/pokemon/1/"
        )

        val pokemon = result.toPokemon()

        assertEquals("Bulbasaur", pokemon.name)
        assertEquals("1", pokemon.id)
        assertEquals("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", pokemon.imageUrl)
    }
}
