package com.example.trabajo_practico_grupo_16.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajo_practico_grupo_16.R
import com.example.trabajo_practico_grupo_16.entities.PokemonEntity

class PokemonAdapter(
    var pokemons: MutableList<PokemonEntity>,
    var context: Context,
    private val onItemClicked: (PokemonEntity) -> Unit // Añadir esta lambda para manejar el clic
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPokemon: TextView = view.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)

        return PokemonViewHolder(view)
    }

    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        val item = pokemons.get(position)
        holder.txtPokemon.text = item.name

        holder.itemView.setOnClickListener {
            onItemClicked(item) // Llama a la función que maneja el clic
        }

    }

}