package com.ardy.pokeappplayground.business.domain.model

import com.ardy.pokeappplayground.business.datasource.cache.database.entity.PokemonEntity

data class Poke(
    var id: Int = 0,
    var url: String = "",
    var name: String = "",
    var imgUrl: String = "",
)

fun Poke.toPokemonEntity() : PokemonEntity{
    return PokemonEntity(
        id = id,
        url = url,
        name = name,
        imgUrl = imgUrl
    )
}

