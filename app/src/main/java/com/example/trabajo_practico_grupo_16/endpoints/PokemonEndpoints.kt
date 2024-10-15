package com.example.trabajo_practico_grupo_16.endpoints

import com.example.trabajo_practico_grupo_16.dtos.Pokemon
import com.example.trabajo_practico_grupo_16.dtos.PokemonDetail
import com.example.trabajo_practico_grupo_16.dtos.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonEndpoints {

    @GET("/pokemon")
    fun getPokemon() : Call<List<Pokemon>> //trae la lista infinita para home

    @GET("pokemon/{id}")
    fun getPokemonDetail(@Path("id") id : Int) : Call<PokemonDetail> //trae un pokemon específico

    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokemonResponse> //limite

    @GET("pokemon")
    suspend fun getPokemonListSuspend(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetailSuspend(@Path("id") id: Int): PokemonDetail

}