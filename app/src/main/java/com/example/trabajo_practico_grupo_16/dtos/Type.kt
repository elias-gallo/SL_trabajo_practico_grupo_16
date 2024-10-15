package com.example.trabajo_practico_grupo_16.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Type(
    val name: String, //Tipo, ej: planta,fuego,etc
    val url: String
)
