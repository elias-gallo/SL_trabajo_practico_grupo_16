package com.example.trabajo_practico_grupo_16

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokemonDao {

    @Query("select * from pokemon_entity")
    fun getAll(): List <Pokemon>

    @Insert
    fun insert(pokemon: Pokemon)

}