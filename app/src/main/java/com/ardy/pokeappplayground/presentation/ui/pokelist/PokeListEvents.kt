package com.ardy.pokeappplayground.presentation.ui.pokelist

sealed class PokeListEvents {

    object GetNewPokemonsEvent: PokeListEvents()

    object GetNextPageEvent: PokeListEvents()

}