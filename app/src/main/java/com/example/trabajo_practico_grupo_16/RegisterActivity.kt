package com.example.trabajo_practico_grupo_16

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    lateinit var etNombreYApellido: EditText
    lateinit var etEmail: EditText
    lateinit var etContraseña: EditText
    lateinit var etRepetirContraseña: EditText
    lateinit var btnVolver: Button
    lateinit var btnContinuar: Button
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNombreYApellido = findViewById(R.id.etNombreYApellido)
        etEmail = findViewById(R.id.etEmail)
        etContraseña = findViewById(R.id.etContraseña)
        etRepetirContraseña = findViewById(R.id.etRepetirContraseña)
        btnVolver = findViewById(R.id.btnVolver)
        btnContinuar = findViewById(R.id.btnContinuar)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)

        btnVolver.setOnClickListener {
            val intentLogin = Intent(this, MainActivity::class.java)
            startActivity(intentLogin)
            finish()
        }

        btnContinuar.setOnClickListener {
            val nombre = etNombreYApellido.text.toString()
            val email = etEmail.text.toString()
            val password = etContraseña.text.toString()
            val repetirPassword = etRepetirContraseña.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || repetirPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else if (password != repetirPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                registrarUsuario(nombre, email, password)
            }
        }
    }

    private fun registrarUsuario(nombre: String, email: String, password: String) {
        val usuarioDao = AppDataBase.getDataBase(this).usuarioDao()
        val nuevoUsuario = Usuario(nombre = nombre, email = email, password = password)

        GlobalScope.launch {
            usuarioDao.insertarUsuario(nuevoUsuario)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@RegisterActivity, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}