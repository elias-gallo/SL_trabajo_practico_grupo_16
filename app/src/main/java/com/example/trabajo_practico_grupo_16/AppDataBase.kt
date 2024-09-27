package com.example.trabajo_practico_grupo_16

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =  [Pokemon::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun pokemonDao() : PokemonDao

    companion object{
        private var INSTANCIA : AppDataBase? = null
        fun getDataBase(context : Context) : AppDataBase{
            if (INSTANCIA == null){
                synchronized(this){
                    INSTANCIA = Room.databaseBuilder(
                        context,AppDataBase::class.java,
                        "pokemon_database").allowMainThreadQueries().fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCIA!!
        }

    }

}