package com.example.trabajo_practico_grupo_16.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.trabajo_practico_grupo_16.R

class PrimerFragmento : Fragment(R.layout.primer_fragmento) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Encontrar el ImageButton en el layout
        val imageButton: ImageButton = view.findViewById(R.id.btnPrimerFragmento)

        // Configurar el click listener
        imageButton.setOnClickListener {
            // Acción al hacer click en el botón
            // Por ejemplo, abrir el segundo fragmento
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SegundoFragmento())
                .addToBackStack(null)
                .commit()
        }
    }
}