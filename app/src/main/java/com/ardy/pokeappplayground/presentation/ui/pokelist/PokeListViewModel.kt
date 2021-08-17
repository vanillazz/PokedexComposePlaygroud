package com.ardy.pokeappplayground.presentation.ui.pokelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardy.pokeappplayground.business.domain.model.Poke
import com.ardy.pokeappplayground.business.interactors.pokelist.GetPokemon
import com.ardy.pokeappplayground.presentation.ui.pokelist.PokeListEvents.GetNewPokemonsEvent
import com.ardy.pokeappplayground.presentation.ui.pokelist.PokeListEvents.GetNextPageEvent
import com.ardy.pokeappplayground.util.Constants.Companion.PAGINATION_PAGE_SIZE
import com.ardy.pokeappplayground.util.Constants.Companion.TAG
import com.ardy.pokeappplayground.util.DialogQueue
import com.ardy.pokeappplayground.util.connectionutil.ConnectivityManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel
@Inject constructor(
    private val getPokemon: GetPokemon,
    private val connectivityManager: ConnectivityManager,
) : ViewModel() {

    val loading = mutableStateOf(false)
    val dialogQueue = DialogQueue()
    val pokemons: MutableState<List<Poke>> = mutableStateOf(ArrayList())
    val page = mutableStateOf(1)
    var recipeListScrollPosition = 0

    init {
        onTriggerEvent(GetNewPokemonsEvent)
    }

    fun onTriggerEvent(events: PokeListEvents) {
        viewModelScope.launch {
            try {
                when (events) {
                    is GetNewPokemonsEvent -> {
                        getPokemons()
                    }
                    is GetNextPageEvent -> {
                        getNextPage()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private fun getPokemons() {
        resetPageState()
        getPokemon.execute(
            page = page.value,
            offset = page.value - 1,
            isNetworkAvailable = connectivityManager.isNetworkAvailable.value
        ).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
                pokemons.value = list
            }

            dataState.error?.let { errorMsg ->
                dialogQueue.appendErrorMessage("Error", errorMsg)
            }
        }.launchIn(viewModelScope)
    }

    private fun getNextPage() {
        if ((recipeListScrollPosition + 1) >= (page.value * PAGINATION_PAGE_SIZE)) {
            incrementPage()
            if (page.value > 1) {
                getPokemon.execute(
                    page = page.value,
                    offset = (page.value - 1) * PAGINATION_PAGE_SIZE,
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value
                ).onEach { dataState ->
                    loading.value = dataState.loading

                    dataState.data?.let { list ->
                        appendPokemons(list)
                    }

                    dataState.error?.let { errorMsg ->
                        dialogQueue.appendErrorMessage("Error", errorMsg)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun incrementPage() {
        this.page.value = page.value + 1
    }

    private fun appendPokemons(newPokemons: List<Poke>) {
        val current = ArrayList(this.pokemons.value)
        current.addAll(newPokemons)
        this.pokemons.value = current
    }

    private fun resetPageState() {
        pokemons.value = listOf()
        page.value = 1
        onChangeScrollPosition(0)
    }

    fun onChangeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }


}
