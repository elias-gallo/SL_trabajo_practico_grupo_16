package com.example.trabajo_practico_grupo_16.dtos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pokemon(

    val name: String,
    val url: String, //acceder a los datos del pokemon específico

){
    val id: Int
        get() {
            val idString = url.trimEnd('/').substringAfterLast('/')
            return idString.toInt()
        }
}
