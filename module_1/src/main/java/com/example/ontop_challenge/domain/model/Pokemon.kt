package com.example.ontop_challenge.domain.model

import com.example.ontop_challenge.data.datasource.PokemonResult

data class Pokemon(
    val name: String,
    val id: String,
    val imageUrl: String,
)

fun PokemonResult.toPokemon(): Pokemon {
    val id = url.trimEnd('/').substringAfterLast('/')
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    val name = this.name.replaceFirstChar { it.uppercase() }
    return Pokemon(name, id, imageUrl)
}