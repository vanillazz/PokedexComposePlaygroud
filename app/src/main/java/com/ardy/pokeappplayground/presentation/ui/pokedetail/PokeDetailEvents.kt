package com.ardy.pokeappplayground.presentation.ui.pokedetail

sealed class PokeDetailEvents {

    data class GetPokemonDetailEvent(
        val id: Int
    ) : PokeDetailEvents()

}