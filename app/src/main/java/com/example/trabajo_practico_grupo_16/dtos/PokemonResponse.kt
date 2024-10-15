package com.example.trabajo_practico_grupo_16.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponse(
    val results: List<Pokemon>,
    val next: String?
)

