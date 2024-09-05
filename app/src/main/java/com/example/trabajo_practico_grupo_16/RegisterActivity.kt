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

class RegisterActivity : AppCompatActivity() {

    lateinit var etNombreYApellido : EditText
    lateinit var etEmail : EditText
    lateinit var etContraseña : EditText
    lateinit var etRepetirContraseña : EditText
    lateinit var btnVolver : Button
    lateinit var btnContinuar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

        btnVolver.setOnClickListener {

            val intentLogin = Intent(this,MainActivity::class.java)
            startActivity(intentLogin)
            finish()

        }

        btnContinuar.setOnClickListener {

            if(etNombreYApellido.text.toString().isEmpty() || etEmail.text.toString().isEmpty() || etContraseña.text.toString().isEmpty() || etRepetirContraseña.text.toString().isEmpty()){
                Toast.makeText(this,"Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }else{

                Toast.makeText(this,"Usuario creado exitosamente!", Toast.LENGTH_SHORT).show()

                val intentLogin = Intent(this,MainActivity::class.java)
                startActivity(intentLogin)
                finish()
            }
        }



    }
}