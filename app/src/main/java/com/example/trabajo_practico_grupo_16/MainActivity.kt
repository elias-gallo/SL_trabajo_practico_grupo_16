package com.example.trabajo_practico_grupo_16

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var etUsuario : EditText
    lateinit var etContraseña : EditText
    lateinit var btnIniciar : Button
    lateinit var btnRegistrar : Button
    lateinit var cbRecordar : CheckBox
    lateinit var toolbar : Toolbar

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
        cbRecordar = findViewById(R.id.cbRecordar)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.tituloLogin)

        var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        var usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre_usuario), "")
        var passwordGuardado = preferencias.getString(resources.getString(R.string.password_usuario), "")

        if(usuarioGuardado!= "" && passwordGuardado != ""){
            if (usuarioGuardado != null) {
                startHomeActivity(usuarioGuardado)
            }
        }

        btnIniciar.setOnClickListener {

            var mensajeToast = "Iniciando Sesion"
            var usuario = etUsuario.text.toString()
            var pass = etContraseña.text.toString()

            if(usuario.isEmpty() || pass.isEmpty()){

                mensajeToast = "Completar Datos"
                Toast.makeText(this, mensajeToast, Toast.LENGTH_SHORT).show()

            }else{

                if(cbRecordar.isChecked){
                    var preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
                    preferencias.edit().putString(resources.getString(R.string.nombre_usuario), usuario).apply()
                    preferencias.edit().putString(resources.getString(R.string.password_usuario), pass).apply()
                }

               startHomeActivity(usuario)

            }

        }

        btnRegistrar.setOnClickListener {

            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
            finish()

        }


    }

    private fun startHomeActivity(usuario: String) {

        val intentHome = Intent(this,HomeActivity::class.java)
        intentHome.putExtra(resources.getString(R.string.nombre_usuario), usuario)
        startActivity(intentHome)
        finish()

    }
}