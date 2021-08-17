package com.ardy.pokeappplayground.business.datasource.network.pokelist.response
import com.ardy.pokeappplayground.business.datasource.network.pokelist.PokeListDto
import com.google.gson.annotations.SerializedName

data class GetPokeListResultDto(
    @SerializedName("count")
    var count: Int?,

    @SerializedName("results")
    var pokemons: List<PokeListDto>?,
)