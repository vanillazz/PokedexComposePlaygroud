package com.ardy.pokeappplayground.business.interactors.pokedetail

import android.util.Log
import com.ardy.pokeappplayground.business.datasource.network.DataState
import com.ardy.pokeappplayground.business.datasource.network.PokeDetailApiService
import com.ardy.pokeappplayground.business.datasource.network.pokelist.response.toPokeDetail
import com.ardy.pokeappplayground.business.datasource.network.toResponseErrorDetail
import com.ardy.pokeappplayground.business.domain.model.PokeDetail
import com.ardy.pokeappplayground.business.domain.model.urlToId
import com.ardy.pokeappplayground.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GetPokemonDetail(
//    private var pokeDao: PokeDao
    private var service: PokeDetailApiService,
) {
    fun execute(
        pokemonId: Int,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<PokeDetail>> = flow {
        try {
            Log.d(Constants.TAG, "execute: GetPokemonDetail by ID : $pokemonId")
            emit(DataState.loading())

            var pokeDetail: PokeDetail? = null

            if(isNetworkAvailable){
                val parentJob = CoroutineScope(IO).launch {

                    val pokeDetailResult = async {
                        service.getPokeById(pokeId = pokemonId)
                    }.await()

                    val speciesId = pokeDetailResult.species?.url?.let { urlToId(it) } ?: 0
                    val pokeSpeciesResult = async {
                        service.getPokeSpeciesById(speciesId)
                    }.await()

                    val evoChainId = pokeSpeciesResult.evolutionChain?.url?.let { urlToId(it) } ?: 0
                    val pokeEvoChainResult = async {
                        service.getEvolutionChainById(evoChainId)
                    }.await()

                    if (pokeDetailResult != null && pokeSpeciesResult != null && pokeEvoChainResult != null) {
                        pokeDetail =
                            pokeDetailResult.toPokeDetail(pokeSpeciesResult, pokeEvoChainResult)
                    } else {
                        throw Exception("Unable to get Data")
                    }
                }
                parentJob.join()

                if (pokeDetail != null) {
                    pokeDetail?.let { emit(DataState.data(it)) }
                } else {
                    throw Exception("Unable to get Data")
                }
            } else {
                throw Exception("Unable to get Data")
            }

        } catch (e: Throwable) {
            e.printStackTrace()
            Log.e(Constants.TAG, "execute: Exception: ${e}, ${e.cause}")
            emit(DataState.error<PokeDetail>(e.toResponseErrorDetail()))
        }
    }
}