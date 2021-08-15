package com.ardy.pokeappplayground.network.dto
import com.google.gson.annotations.SerializedName

data class GetPokeListResultDto(
    @SerializedName("count") var count: Int?,
    @SerializedName("results") var pokemons: List<ApiValueDto>?,
)