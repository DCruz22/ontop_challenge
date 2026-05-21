package com.example.ontop_challenge.data.datasource

import com.google.gson.annotations.SerializedName

data class PokeListResponse(
    val results: List<PokemonResult>,
)

data class PokemonResult(
    val name: String,
    val url: String,
    val imageUrl: String?,
    val height: Int?,
)

data class PokeDetailResponse(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    @SerializedName("front_default") val frontDefault: String?,
    val height: Int?,
    val weight: Int?,
    val description: String?,
)