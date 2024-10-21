package com.example.trabajo_practico_grupo_16.activities


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trabajo_practico_grupo_16.database.AppDataBase
import com.example.trabajo_practico_grupo_16.R
import com.example.trabajo_practico_grupo_16.entities.guardarSesionUsuario
import com.example.trabajo_practico_grupo_16.entities.obtenerUsuarioRecordado
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var etUsuario: EditText
    lateinit var etContraseña: EditText
    lateinit var btnIniciar: Button
    lateinit var btnRegistrar: Button
    lateinit var cbRecordar: CheckBox
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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





        val usuarioGuardado = obtenerUsuarioRecordado(this)
        if (usuarioGuardado != null) {
            startHomeActivity(usuarioGuardado)
        }

        btnIniciar.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val pass = etContraseña.text.toString()

            if (usuario.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completar Datos", Toast.LENGTH_SHORT).show()
            } else {
                iniciarSesion(usuario, pass)
            }
        }

        btnRegistrar.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
            finish()
        }


        cbRecordar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                crearNotificacion()
            }
        }
    }

    private fun iniciarSesion(email: String, password: String) {
        val usuarioDao = AppDataBase.getDataBase(this).usuarioDao()
        GlobalScope.launch(Dispatchers.IO) {
            val usuario = usuarioDao.obtenerUsuario(email, password)
            withContext(Dispatchers.Main) {
                if (usuario != null) {
                    if (cbRecordar.isChecked) {
                        guardarSesionUsuario(this@MainActivity, email)
                    }
                    startHomeActivity(email)
                } else {
                    Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startHomeActivity(usuario: String) {
        val intentHome = Intent(this, HomeActivity::class.java) //TestActivity cambiar a HomeActivity
        intentHome.putExtra(resources.getString(R.string.nombre_usuario), usuario)
        startActivity(intentHome)
        finish()
    }

    private fun crearNotificacion() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "checkbox_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, "CheckBox Channel", importance).apply {
                description = "Canal para notificaciones del CheckBox"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_check_24)
            .setContentTitle("Notificación de CheckBox")
            .setContentText("Su usuario será recordado")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(1, notificationBuilder.build())
        }
    }

}