package com.ardy.pokeappplayground.network.dto
import com.google.gson.annotations.SerializedName

data class GetPokeDetailResultDto(
    @SerializedName("name") var name: String?,
    @SerializedName("order") var order: Int?,
    @SerializedName("stats") var stats: List<PokeStatDto>?,
    @SerializedName("types") var types: List<PokeTypeDto>?,
    @SerializedName("weight") var weight: Int?,
    @SerializedName("species") var species: ApiValueDto?,
)

data class PokeStatDto(
    @SerializedName("base_stat") var baseStat: Int?,
    @SerializedName("stat") var stat: ApiValueDto?,
)

data class PokeTypeDto(
    @SerializedName("slot") var slot: Int?,
    @SerializedName("type") var type: ApiValueDto?,
)