package com.example.trabajo_practico_grupo_16

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class ListadoPokemonActivity : AppCompatActivity() {

    lateinit var rvPokemon : RecyclerView
    lateinit var pokemonAdapter: PokemonAdapter
    lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_pokemon)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = resources.getString(R.string.titulo)

        rvPokemon = findViewById(R.id.rvPokemon)
        pokemonAdapter = PokemonAdapter(getPokemon(), this)
            { pokemon->mostrarDatosPokemon(pokemon) }

        rvPokemon.adapter = pokemonAdapter
    }

    private fun getPokemon(): MutableList<Pokemon> {

        var pokemon : MutableList<Pokemon> = ArrayList()

        pokemon.add(Pokemon(1, "Bulbasaur", 7, 69, 64))
        pokemon.add(Pokemon(4, "Charmander", 6, 85, 62))
        pokemon.add(Pokemon(7, "Squirtle", 5, 90, 63))
        pokemon.add(Pokemon(25, "Pikachu", 4, 60, 112))
        pokemon.add(Pokemon(133, "Eevee", 3, 65, 65))
        pokemon.add(Pokemon(150, "Mewtwo", 20, 1220, 306))

        return pokemon;
    }

    private fun mostrarDatosPokemon(pokemon: Pokemon) {
        val mensaje = "Tipo: ${pokemon.name}, Peso: ${pokemon.weight}, Altura: ${pokemon.height},id:${pokemon.id}"
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.item_volver){
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}