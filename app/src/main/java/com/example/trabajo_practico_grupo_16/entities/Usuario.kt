package com.example.trabajo_practico_grupo_16.entities

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")// ESTO LO HICISTE VOS ELIAS, VOLVI A CREAR ESTE ARCHIVO POR LOS PROBLEMAS DE COMPATIBILIDAD QUE TUVE
public class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String
)
fun guardarSesionUsuario(context: Context, email: String) {
    val sharedPreferences = context.getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("email", email)
    editor.apply()
}
fun obtenerUsuarioRecordado(context: Context): String?{
    val sharedPreferences = context.getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE)
    return sharedPreferences.getString("email", null)
}