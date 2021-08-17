package com.ardy.pokeappplayground.business.datasource.network.pokedetail.response

import com.ardy.pokeappplayground.business.datasource.network.pokedetail.response.CommonNameUrlDto
import com.google.gson.annotations.SerializedName

data class GetPokeSpeciesResultDto(
    @SerializedName("color") var color: CommonNameUrlDto?,
    @SerializedName("evolution_chain") var evolutionChain: CommonNameUrlDto?,
    @SerializedName("egg_groups") var eggGroups: List<CommonNameUrlDto>?,
    @SerializedName("habitat") var habitat: CommonNameUrlDto?,
)