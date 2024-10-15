package com.example.trabajo_practico_grupo_16.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajo_practico_grupo_16.R
import com.example.trabajo_practico_grupo_16.activities.PokemonDetailActivity
import com.example.trabajo_practico_grupo_16.dtos.Pokemon
import com.example.trabajo_practico_grupo_16.dtos.PokemonDetail
import com.squareup.picasso.Picasso

class PokemonAdapterApi(private val pokemonList: MutableList<Pokemon>) : RecyclerView.Adapter<PokemonAdapterApi.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_api, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.pokemonName.text = pokemon.name.capitalize()
        holder.pokemonId.text = "ID: ${pokemon.id}"

    }

    override fun getItemCount(): Int = pokemonList.size

    // Función para agregar más Pokémon a la lista
    fun addPokemonList(newPokemons: List<Pokemon>) {
        pokemonList.addAll(newPokemons)
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView = itemView.findViewById(R.id.tvPokemonName)
        val pokemonId: TextView = itemView.findViewById(R.id.tvPokemonId)


        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedPokemon = pokemonList[position]
                    val intent = Intent(itemView.context, PokemonDetailActivity::class.java).apply {
                        putExtra("pokemonId", clickedPokemon.id)
                        putExtra("pokemonName", clickedPokemon.name)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}