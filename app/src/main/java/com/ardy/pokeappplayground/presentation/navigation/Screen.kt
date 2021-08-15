package com.ardy.pokeappplayground.presentation.navigation

sealed class Screen(
    val route: String,
){
    object PokeList: Screen("pokeList")

    object PokeDetail: Screen("pokeDetail")
}