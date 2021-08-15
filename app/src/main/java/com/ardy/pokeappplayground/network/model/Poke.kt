package com.ardy.pokeappplayground.network.model

import com.ardy.pokeappplayground.network.dto.ApiValueDto


open class Poke(
    var id: Int = 0,
    var name: String = ""
) {
    constructor(dto: ApiValueDto?) : this(
        id = dto?.url?.let { urlToId(it) } ?: 0,
        name = dto?.name.orEmpty()
    )
}

