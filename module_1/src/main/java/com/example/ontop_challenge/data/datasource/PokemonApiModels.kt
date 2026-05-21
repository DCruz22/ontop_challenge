package com.example.ontop_challenge.data.datasource

import com.google.gson.annotations.SerializedName

data class PokeListResponse(
    val results: List<PokemonResult>,
)

data class PokemonResult(
    val name: String,
    val url: String,
    val imageUrl: String? = null,
    val height: Int? = null,
    val id: String? = null,
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val height: Int?,
    val weight: Int?,
    val abilities: List<AbilityEntry>? = null,
)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String?,
)

data class AbilityEntry(
    val ability: Ability,
)

data class Ability(
    val name: String,
)