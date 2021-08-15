package com.ardy.pokeappplayground.network.model

import com.ardy.pokeappplayground.network.dto.PokeTypeDto

data class PokeType(
    var slot: Int = 0,
    var type: NamedApiResource? = null
) {
    constructor(dto: PokeTypeDto?) : this(
        slot = dto?.slot ?: 0,
        type = NamedApiResource(dto?.type)
    )
}

