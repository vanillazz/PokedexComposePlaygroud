package com.ardy.pokeappplayground.presentation.ui.pokedetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardy.pokeappplayground.business.domain.model.PokeDetail
import com.ardy.pokeappplayground.business.interactors.pokedetail.GetPokemonDetail
import com.ardy.pokeappplayground.util.Constants
import com.ardy.pokeappplayground.util.Constants.Companion.TAG
import com.ardy.pokeappplayground.util.DialogQueue
import com.ardy.pokeappplayground.util.connectionutil.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeDetailViewModel
@Inject constructor(
    private val getPokemonDetail: GetPokemonDetail,
    private val connectivityManager: ConnectivityManager,
) : ViewModel() {

    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()
    val onLoad: MutableState<Boolean> = mutableStateOf(false)

    val pokemonDetail: MutableState<PokeDetail?> = mutableStateOf(null)

    fun onTriggerEvent(events: PokeDetailEvents) {
        viewModelScope.launch {
            try {
                when (events) {
                    is PokeDetailEvents.GetPokemonDetailEvent -> {
                        getPokemonDetail(events.id)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJobDetail: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJobDetail : finally called.")
            }
        }

    }

    private fun getPokemonDetail(pokemonId: Int) {
        Log.d(TAG, "getPokemonDetail with id : $pokemonId")
        getPokemonDetail.execute(
            pokemonId = pokemonId,
            isNetworkAvailable = connectivityManager.isNetworkAvailable.value
        ).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { data ->
                pokemonDetail.value = data
            }

            dataState.error?.let { error ->
                Log.e(TAG, "getPokemonDetail: ${error}")
                dialogQueue.appendErrorMessage("Error", error)
            }

        }.launchIn(viewModelScope)
    }
}
