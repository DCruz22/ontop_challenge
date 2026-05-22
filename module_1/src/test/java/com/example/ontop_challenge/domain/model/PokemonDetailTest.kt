package com.example.ontop_challenge.domain.model

import com.example.ontop_challenge.data.datasource.Ability
import com.example.ontop_challenge.data.datasource.AbilityEntry
import com.example.ontop_challenge.data.datasource.PokemonDetailResponse
import com.example.ontop_challenge.data.datasource.Sprites
import org.junit.Assert.assertEquals
import org.junit.Test

class PokemonDetailTest {

    @Test
    fun `toPokemonDetail maps PokemonDetailResponse correctly`() {
        val response = PokemonDetailResponse(
            id = 25,
            name = "pikachu",
            sprites = Sprites("https://pikachu.png"),
            height = 4,
            weight = 60,
            abilities = listOf(
                AbilityEntry(Ability("static")),
                AbilityEntry(Ability("lightning-rod"))
            )
        )

        val detail = response.toPokemonDetail()

        assertEquals(25, detail.id)
        assertEquals("Pikachu", detail.name)
        assertEquals("https://pikachu.png", detail.imageUrl)
        assertEquals(4, detail.height)
        assertEquals(60, detail.weight)
        assertEquals("Static, Lightning-rod", detail.abilities)
    }
}
