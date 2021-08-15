package com.ardy.pokeappplayground.network

import com.ardy.pokeappplayground.network.dto.GetPokeDetailResultDto
import com.ardy.pokeappplayground.network.dto.GetPokeListResultDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("pokemon")
    suspend fun getPokeList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : GetPokeListResultDto

    @GET("pokemon/{id}/")
    suspend fun getPokeById(
        @Path("id") pokeId: Int,
    ) : GetPokeDetailResultDto

    @GET("pokemon-species/{id}/")
    suspend fun getPokeSpeciesById(
        @Path("id") speciesId: Int,
    ) : GetPokeDetailResultDto

    @GET("evolution-chain/{id}/")
    suspend fun getEvolutionChainById(
        @Path("id") evoChainId: Int,
    ) : GetPokeDetailResultDto
}