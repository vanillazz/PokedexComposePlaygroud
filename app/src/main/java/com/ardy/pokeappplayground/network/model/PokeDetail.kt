package com.ardy.pokeappplayground.network.model

import com.ardy.pokeappplayground.network.dto.GetPokeDetailResultDto


data class PokeDetail(
    var order: Int = 0,
    var stats: List<PokeStat> = listOf(),
    var types: List<PokeType> = listOf(),
    var weight: Int = 0,
    var species: NamedApiResource? = null,
) : Poke() {
    constructor(dto: GetPokeDetailResultDto?) : this(
        order = dto?.order ?: 0,
        stats = dto?.stats?.map { PokeStat(it) }.orEmpty(),
        types = dto?.types?.map { PokeType(it) }.orEmpty(),
        weight = dto?.order ?: 0,
        species = NamedApiResource(dto?.species),
    ) {
        id = dto?.order ?: 0
        name = dto?.name.orEmpty()
    }
}

