package com.ardy.pokeappplayground.network.model

import com.ardy.pokeappplayground.network.dto.PokeStatDto

data class PokeStat(
    var baseStat: Int = 0,
    var stats: NamedApiResource? = null
) {
    constructor(dto: PokeStatDto?) : this(
        baseStat = dto?.baseStat ?: 0,
        stats = NamedApiResource(dto?.stat)
    )
}

