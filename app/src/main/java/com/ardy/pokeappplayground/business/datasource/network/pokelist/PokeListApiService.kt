package com.ardy.pokeappplayground.business.datasource.network

import com.ardy.pokeappplayground.business.datasource.network.pokelist.response.GetPokeListResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeListApiService {

    @GET("pokemon")
    suspend fun getPokeList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : GetPokeListResultDto
}