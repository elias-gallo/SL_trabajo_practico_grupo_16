package com.example.trabajo_practico_grupo_16.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trabajo_practico_grupo_16.entities.PokemonEntity

@Dao
interface PokemonDao {

    @Query("select * from pokemon_entity")
    fun getAll(): List <PokemonEntity>

    @Insert
    fun insert(pokemon: PokemonEntity)

}