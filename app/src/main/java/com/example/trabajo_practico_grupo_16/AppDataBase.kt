package com.example.trabajo_practico_grupo_16

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pokemon::class, Usuario::class], version = 2)
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


