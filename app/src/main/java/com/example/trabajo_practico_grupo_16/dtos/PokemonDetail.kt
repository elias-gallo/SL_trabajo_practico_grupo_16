package com.example.trabajo_practico_grupo_16.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetail(

    val id: Int?,                              // ID del Pokémon (número en la Pokédex)
    val name: String,                         // Nombre del Pokémon
    val height: Int,                          // Altura del Pokémon
    val weight: Int,                          // Peso del Pokémon
    val base_experience: Int,                 // Experiencia base del Pokémon
    val sprites: Sprites,                     // Imágenes del Pokémon
    val types: List<TypeWrapper>              // Tipos del Pokémon

)
