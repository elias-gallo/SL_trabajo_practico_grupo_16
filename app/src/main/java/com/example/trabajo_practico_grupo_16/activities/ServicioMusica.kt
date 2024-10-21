package com.example.trabajo_practico_grupo_16.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.trabajo_practico_grupo_16.R

class ServicioMusica : Service() {

    private var mediaPlayer: MediaPlayer? =null
    private val Chanel_ID: String="Musica"
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val songResId = intent?.getIntExtra("SONG_RES_ID", R.raw.rojoyfuego)
        Log.d("ServicioMusica", "Intent recibido. Acción: ${intent?.action}, ID canción: $songResId")

        // Llama a startForeground() inmediatamente
        val notificacion = crearNotificacion()  // Crear la notificación
        startForeground(1, notificacion)

        if (intent?.getBooleanExtra("pause", false) == true) {
            // Si se presionó el botón de pausar/reanudar
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            } else {
                mediaPlayer?.start()
            }
        } else if (intent?.getBooleanExtra("stop", false) == true) {
            // Si se presionó el botón de detener
            stopSelf() // Detenemos el servicio
        } else {
            // Si el MediaPlayer aún no está configurado
            if (mediaPlayer == null) {
                configurarMediaPlayer(songResId ?: R.raw.rojoyfuego)
            }
            mediaPlayer?.start()
            Log.d("ServicioMusica", "Reproduciendo canción.")
        }

        return START_STICKY
    }

    private fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "music_channel_id",
                "Reproducción de música",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Verifica si el canal ya ha sido creado (solo necesario en versiones >= API 26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "music_channel_id"
            val channelName = "Canal de música"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = "Canal para controlar la música"

            // Crea el canal de notificación
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun crearNotificacion(): Notification {
        val intentPause = Intent(this, ServicioMusica::class.java)
        intentPause.putExtra("pause", true)
        val piPause = PendingIntent.getService(
            this, 1, intentPause, PendingIntent.FLAG_IMMUTABLE
        )

        val intentStop = Intent(this, ServicioMusica::class.java)
        intentStop.putExtra("stop", true)
        val piStopService = PendingIntent.getService(
            this, 2, intentStop, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, "music_channel_id")
            .setContentTitle("Reproduciendo música")
            .setSmallIcon(R.drawable.baseline_play_arrow_24)
            .addAction(R.drawable.baseline_pause_circle_24, "Pause", piPause)
            .addAction(R.drawable.baseline_stop_24, "Stop", piStopService)
            .setOngoing(true)
            .build()
    }
    private fun configurarMediaPlayer(songResId: Int) {
        Log.d("ServicioMusica", "Configurando MediaPlayer con canción ID: $songResId")
        mediaPlayer = MediaPlayer.create(this, songResId)
        mediaPlayer?.setOnCompletionListener {
            Log.d("ServicioMusica", "Canción finalizada.")
            stop()
        }
        if (mediaPlayer == null) {
            Log.e("ServicioMusica", "El MediaPlayer no pudo ser creado.")
        }
    }
    private fun stop(){
        stopNotification()
        stopMusic()
        stopSelf()
    }
    private fun stopMusic(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer=null
    }
    private fun stopNotification(){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            NotificationManagerCompat.from(this).cancel(1)
        }
    }
    override fun onDestroy() {
        stopMusic()
        stopNotification()
        super.onDestroy()
    }
}
