package com.example.trabajo_practico_grupo_16.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypeWrapper(
    val slot: Int, // 1 o 2
    val type: Type // tipo
)
