package com.ardy.pokeappplayground.business.interactors.pokelist

import android.util.Log
import com.ardy.pokeappplayground.business.datasource.cache.database.dao.PokemonDao
import com.ardy.pokeappplayground.business.datasource.cache.database.entity.toPoke
import com.ardy.pokeappplayground.business.datasource.network.DataState
import com.ardy.pokeappplayground.business.datasource.network.PokeListApiService
import com.ardy.pokeappplayground.business.datasource.network.pokelist.toPoke
import com.ardy.pokeappplayground.business.datasource.network.toResponseErrorDetail
import com.ardy.pokeappplayground.business.domain.model.Poke
import com.ardy.pokeappplayground.business.domain.model.toPokemonEntity
import com.ardy.pokeappplayground.util.Constants
import com.ardy.pokeappplayground.util.Constants.Companion.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemon(
    private var cache: PokemonDao,
    private var service: PokeListApiService
) {
    fun execute(
        page: Int, // start from
        offset: Int, // start from
        isNetworkAvailable: Boolean,
    ): Flow<DataState<List<Poke>>> = flow {

        try {  //catch network exception
            Log.d(TAG, "execute: GetPokemon start from $offset")
            emit(DataState.loading<List<Poke>>())

            if(isNetworkAvailable){
                val pokemons = service.getPokeList(
                    Constants.PAGINATION_PAGE_SIZE,
                    offset
                ).pokemons?.map { it.toPoke() }.orEmpty()

                //insert to cache
                cache.insertPokemons(pokemons.map { it.toPokemonEntity() })
            }

            val list = cache.getAllPokemons(page = page).map { it.toPoke() }
            emit(DataState.data(list))

        } catch (e: Throwable) {
            e.printStackTrace()
            Log.e(TAG, "execute: Exception: ${e}, ${e.cause}")
            emit(DataState.error<List<Poke>>(e.toResponseErrorDetail()))
        }


    }

}