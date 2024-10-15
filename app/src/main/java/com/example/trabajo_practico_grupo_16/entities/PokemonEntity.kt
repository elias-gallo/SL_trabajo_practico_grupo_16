package com.example.trabajo_practico_grupo_16.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_entity")
data class PokemonEntity(

    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "height") var height : Int,
    @ColumnInfo(name = "weight")var weight : Int,
    @ColumnInfo(name = "base_experience")var base_experience : Int

){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
