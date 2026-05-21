package com.example.ontop_challenge.domain.model

import com.example.ontop_challenge.data.datasource.PokemonDetailResponse

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val height: Int?,
    val weight: Int?,
    val abilities: String?,
)

fun PokemonDetailResponse.toPokemonDetail(): PokemonDetail {
    return PokemonDetail(
        id = this.id,
        name = this.name.replaceFirstChar { it.uppercase() },
        imageUrl = this.sprites.frontDefault,
        height = this.height,
        weight = this.weight,
        abilities = this.abilities?.map { it.ability.name.replaceFirstChar { char -> char.uppercase() } }
            ?.take(3)?.joinToString(", ")
    )
}