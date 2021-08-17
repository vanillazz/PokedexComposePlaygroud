package com.ardy.pokeappplayground.business.datasource.network

import com.ardy.pokeappplayground.business.datasource.network.pokedetail.response.GetPokeEvoChainResultDto
import com.ardy.pokeappplayground.business.datasource.network.pokedetail.response.GetPokeSpeciesResultDto
import com.ardy.pokeappplayground.business.datasource.network.pokelist.response.GetPokeDetailResultDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeDetailApiService {

    @GET("pokemon/{id}/")
    suspend fun getPokeById(
        @Path("id") pokeId: Int,
    ) : GetPokeDetailResultDto

    @GET("pokemon-species/{id}/")
    suspend fun getPokeSpeciesById(
        @Path("id") speciesId: Int,
    ) : GetPokeSpeciesResultDto

    @GET("evolution-chain/{id}/")
    suspend fun getEvolutionChainById(
        @Path("id") evoChainId: Int,
    ) : GetPokeEvoChainResultDto

}