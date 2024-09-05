package com.example.trabajo_practico_grupo_16

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var etUsuario : EditText
    lateinit var etContraseña : EditText
    lateinit var btnIniciar : Button
    lateinit var btnRegistrar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etUsuario = findViewById(R.id.etUsuario)
        etContraseña = findViewById(R.id.etContraseña)
        btnIniciar = findViewById(R.id.btnIniciar)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnIniciar.setOnClickListener {

            var mensajeToast = "Iniciando Sesion"

            if(etUsuario.text.toString().isEmpty() || etContraseña.text.toString().isEmpty()){

            mensajeToast = "Faltan datos, por favor complete todos los campos."

            }else{

                val intentHome = Intent(this,HomeActivity::class.java)
                startActivity(intentHome)
                finish()

            }

            Toast.makeText(this, mensajeToast, Toast.LENGTH_SHORT).show()

        }

        btnRegistrar.setOnClickListener {

            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
            finish()

        }


    }
}