package com.example.trabajo_practico_grupo_16.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trabajo_practico_grupo_16.dao.PokemonDao
import com.example.trabajo_practico_grupo_16.dao.UsuarioDao
import com.example.trabajo_practico_grupo_16.entities.PokemonEntity
import com.example.trabajo_practico_grupo_16.entities.Usuario

@Database(entities = [PokemonEntity::class, Usuario::class], version = 3)
abstract class AppDataBase : RoomDatabase() {

    abstract fun pokemonDao() : PokemonDao
    abstract fun usuarioDao(): UsuarioDao
    companion object {
            @Volatile
            private var INSTANCIA: AppDataBase? = null

            fun getDataBase(context: Context): AppDataBase {
                return INSTANCIA ?: synchronized(this) {
                    val instancia = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "pokemon_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCIA = instancia
                    instancia
                }
            }
        }
    }


