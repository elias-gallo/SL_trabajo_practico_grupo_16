package com.example.trabajo_practico_grupo_16.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.trabajo_practico_grupo_16.R
import com.example.trabajo_practico_grupo_16.configurations.RetrofitClient
import com.example.trabajo_practico_grupo_16.dtos.PokemonDetail
import com.example.trabajo_practico_grupo_16.endpoints.PokemonEndpoints
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var tvPokemonName: TextView
    private lateinit var tvPokemonId: TextView
    private lateinit var tvPokemonTypes: TextView
    private lateinit var tvPokemonHeight: TextView
    private lateinit var tvPokemonWeight: TextView
    private lateinit var tvPokemonBaseExperience: TextView
    private lateinit var ivPokemonSpriteFront: ImageView
    private lateinit var ivPokemonSpriteBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.tituloPokemonDetail)

        // Obtenengo el ID y nombre del Pokémon desde el Intent
        val pokemonId = intent?.getIntExtra("pokemonId", -1) ?: -1
        val pokemonName = intent?.getStringExtra("pokemonName") ?: "Unknown"

        Log.d("PokemonDetailActivity", "Pokemon Recibido: $pokemonName, ID: $pokemonId")

        tvPokemonName = findViewById(R.id.tvPokemonName)
        tvPokemonId = findViewById(R.id.tvPokemonId)
        tvPokemonTypes = findViewById(R.id.tvPokemonTypes)
        tvPokemonHeight = findViewById(R.id.tvPokemonHeight)
        tvPokemonWeight = findViewById(R.id.tvPokemonWeight)
        tvPokemonBaseExperience = findViewById(R.id.tvPokemonBaseExp)
        ivPokemonSpriteFront = findViewById(R.id.ivPokemonSpriteFront)
        ivPokemonSpriteBack = findViewById(R.id.ivPokemonSpriteBack)


        tvPokemonName.text = pokemonName
        tvPokemonId.text = "ID: $pokemonId"

        //Llamamos a la api
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val api = RetrofitClient.retrofit.create(PokemonEndpoints::class.java)
                val pokemonDetail = api.getPokemonDetailSuspend(pokemonId)

                // Cambiamos de hilo
                withContext(Dispatchers.Main) {
                    if (pokemonDetail != null) {

                        Picasso.get()
                            .load(pokemonDetail.sprites.front_default)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(ivPokemonSpriteFront)

                        Picasso.get()
                            .load(pokemonDetail.sprites.back_default)
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(ivPokemonSpriteBack)

                        // Mostrar detalles del Pokémon
                        tvPokemonTypes.text = "Type: ${pokemonDetail.types.joinToString(", ") { it.type.name }}"
                        tvPokemonHeight.text = "Height: ${pokemonDetail.height / 10.0} m"
                        tvPokemonWeight.text = "Weight: ${pokemonDetail.weight / 10.0} kg"
                        tvPokemonBaseExperience.text = "Base Experience: ${pokemonDetail.base_experience}"
                    } else {
                        Log.e("PokemonDetailActivity", "El cuerpo de la respuesta está vacío")
                    }
                }
            } catch (e: Exception) {
                Log.e("PokemonDetailActivity", "Error al obtener detalles: ${e.localizedMessage}")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_volver -> {
                startActivity(Intent(this, HomeActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}