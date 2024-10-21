package com.example.trabajo_practico_grupo_16.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajo_practico_grupo_16.R
import com.example.trabajo_practico_grupo_16.adapters.PokemonAdapterApi
import com.example.trabajo_practico_grupo_16.configurations.RetrofitClient
import com.example.trabajo_practico_grupo_16.dtos.Pokemon
import com.example.trabajo_practico_grupo_16.dtos.PokemonResponse
import com.example.trabajo_practico_grupo_16.endpoints.PokemonEndpoints
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapterApi
    private var pokemons = mutableListOf<Pokemon>()
    private var isLoading = false
    private var offset = 0
    private val limit = 20

    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        pokemonAdapter = PokemonAdapterApi(pokemons)
        recyclerView.adapter = pokemonAdapter

        //Cuando llegamos al final del scroll, se cargan mas pokemon
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == pokemons.size - 1) {
                    cargarMasPokemon()
                }
            }
        })

        saludarUsuario()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.tituloHome)

        // Cargo los primeros Pokémon
        cargarMasPokemon()




        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PrimerFragmento())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_listado -> {
                val intent = Intent(this, ListadoPokemonActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.item_salir -> {
                cerrarSesion()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun saludarUsuario() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val usuario = bundle.getString(resources.getString(R.string.nombre_usuario))
            Toast.makeText(this, "Bienvenido/a $usuario", Toast.LENGTH_LONG).show()
        }
    }

    private fun cerrarSesion() {
        val sharedPreferences = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Elimina todos los datos guardados
        editor.apply()

        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun cargarMasPokemon() {
        if (isLoading) return
        isLoading = true

        // Usamos corutinas
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                //Llamo a la API
                val api = RetrofitClient.retrofit.create(PokemonEndpoints::class.java)
                val pokemonResponse = api.getPokemonListSuspend(limit, offset)

                // Cambiamos de hilo
                withContext(Dispatchers.Main) {
                    if (pokemonResponse != null) {
                        offset += limit
                        pokemons.addAll(pokemonResponse.results)  // Añado más pokemon a la lista
                        pokemonAdapter.notifyDataSetChanged()
                    }
                    isLoading = false
                }
            } catch (e: Exception) {
                Log.e("ERROR", "Error al cargar Pokémon: ${e.message}")
                withContext(Dispatchers.Main) {
                    isLoading = false
                }
            }
        }
    }
}