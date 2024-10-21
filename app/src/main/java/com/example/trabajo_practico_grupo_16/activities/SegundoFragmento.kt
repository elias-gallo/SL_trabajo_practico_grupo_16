package com.example.trabajo_practico_grupo_16.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.trabajo_practico_grupo_16.R

class SegundoFragmento : Fragment(R.layout.segundo_fragmento) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCancion1: ImageButton = view.findViewById(R.id.btnSeleccionarCancion1)
        val btnCancion2: ImageButton = view.findViewById(R.id.btnSeleccionarCancion2)

        // Reproduce la primera canción cuando se presiona el primer botón
        btnCancion1.setOnClickListener {
            reproducirCancion(R.raw.rojoyfuego)
            Toast.makeText(requireContext(), "Reproduciendo intro Rojo y fuego", Toast.LENGTH_SHORT).show()
        }

        // Reproduce la segunda canción cuando se presiona el segundo botón
        btnCancion2.setOnClickListener {
            reproducirCancion(R.raw.esmeralda)

            Toast.makeText(requireContext(), "Reproduciendo intro Esmeralda", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reproducirCancion(cancionResId: Int) {
        val intent = Intent(requireContext(), ServicioMusica::class.java).apply {
            action = "PLAY"
            putExtra("SONG_RES_ID", cancionResId)
        }
        ContextCompat.startForegroundService(requireContext(), intent)
        Log.d("SegundoFragmento", "Intent enviado con canción ID: $cancionResId")
    }
}