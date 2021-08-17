package com.ardy.pokeappplayground.business.datasource.network.pokelist.response

import com.ardy.pokeappplayground.business.datasource.network.pokedetail.response.CommonNameUrlDto
import com.ardy.pokeappplayground.business.datasource.network.pokedetail.response.GetPokeEvoChainResultDto
import com.ardy.pokeappplayground.business.datasource.network.pokedetail.response.GetPokeSpeciesResultDto
import com.ardy.pokeappplayground.business.datasource.network.pokedetail.response.toCommonNameUrl
import com.ardy.pokeappplayground.business.domain.model.*
import com.ardy.pokeappplayground.util.Constants
import com.ardy.pokeappplayground.util.capitalizeWords
import com.google.gson.annotations.SerializedName

data class GetPokeDetailResultDto(
    @SerializedName("id") var id: Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("order") var order: Int?,
    @SerializedName("abilities") var abilities: List<PokeAbilityDto>?,
    @SerializedName("stats") var stats: List<PokeStatDto>?,
    @SerializedName("types") var types: List<PokeTypeDto>?,
    @SerializedName("weight") var weight: Int?,
    @SerializedName("species") var species: CommonNameUrlDto?,
)

data class PokeAbilityDto(
    @SerializedName("ability") var ability: CommonNameUrlDto?,
)

fun PokeAbilityDto.toPokeAbitity(): PokeAbility{
    return PokeAbility(ability = ability?.toCommonNameUrl())
}

data class PokeStatDto(
    @SerializedName("base_stat") var baseStat: Int?,
    @SerializedName("stat") var stat: CommonNameUrlDto?,
)

fun PokeStatDto.toPokeStat(): PokeStat{


    var currentStatName = stat?.name?.lowercase().orEmpty()
    var newStatName = ""
    if(currentStatName.contains("special-attack")){
        newStatName = currentStatName.replace("special-attack", "Sp. Atk")
    }
    else if(currentStatName.contains("special-defense")){
        newStatName = currentStatName.replace("special-defense", "Sp. Def")
    } else {
        newStatName = currentStatName
    }
    return PokeStat(
        baseStat = baseStat ?: 0,
        stat = newStatName.capitalizeWords()
    )
}

data class PokeTypeDto(
    @SerializedName("slot") var slot: Int?,
    @SerializedName("type") var type: CommonNameUrlDto?,
)

fun PokeTypeDto.toPokeType(): PokeType {
    return PokeType(
        slot = slot ?: 0,
        type = type?.name.orEmpty()
    )
}

fun GetPokeDetailResultDto.toPokeDetail(
    pokeSpecies: GetPokeSpeciesResultDto,
    pokeEvoChain: GetPokeEvoChainResultDto,
): PokeDetail {
    val imgName = (id ?: 0).toString().padStart(3, '0')

    return PokeDetail(

        //detail
        id = id ?: 0,
        name = name.orEmpty(),
        imgUrl = Constants.POKE_DETAIL_IMG_URL + imgName + ".png",
        order = order ?: 0,
        abilities = abilities?.map { it.toPokeAbitity() }.orEmpty(),
        stats = stats?.map { it.toPokeStat() }.orEmpty(),
        types = types?.map { it.toPokeType() }.orEmpty(),
        weight = weight ?: 0,
        species = species?.toCommonNameUrl(),

        //species
        eggGroups = pokeSpecies.eggGroups?.map { it.name.orEmpty() } ?: listOf(),
        habitat = pokeSpecies.habitat?.name,
        evolutionChainUrl = pokeSpecies.evolutionChain?.toCommonNameUrl(),

        //evo chain
        evoChain = listOf()

    )
}