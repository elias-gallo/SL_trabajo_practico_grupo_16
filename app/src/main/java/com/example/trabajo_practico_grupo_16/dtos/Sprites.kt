package com.example.trabajo_practico_grupo_16.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sprites(
    val front_default: String, //img frontal
    val back_default: String //img trasera
)
