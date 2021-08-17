package com.ardy.pokeappplayground.business.domain.model

import androidx.compose.ui.graphics.Color
import com.ardy.pokeappplayground.presentation.theme.*

data class PokeDetail(
    //detail
    var id: Int = 0,
    var name: String = "",
    var imgUrl: String = "",
    var order: Int = 0,
    var abilities: List<PokeAbility> = listOf(),
    var stats: List<PokeStat> = listOf(),
    var types: List<PokeType> = listOf(),
    var weight: Int = 0,
    var species: CommonNameUrl? = null,

    //species
    var eggGroups: List<String> = listOf(),
    var habitat: String? = null,
    var evolutionChainUrl: CommonNameUrl? = null,

    //evo-chain
    var evoChain: List<PokeEvoChain> = listOf()

)

data class PokeAbility(
    var ability: CommonNameUrl? = null
)

data class PokeStat(
    var baseStat: Int = 0,
    var stat: String = ""
)

data class PokeType(
    var slot: Int = 0,
    var type: String = ""
)

data class PokeEvoChain(
    var name: String = "",
    var speciesUrl: String? = null
)

fun PokeDetail.bgcolor(): Color {
    val type = types.elementAtOrNull(0)

    return when (type?.type?.lowercase()) {
        "grass", "bug" -> pokelightteal
        "fire" -> pokelightred
        "water", "fighting", "normal" -> pokelightblue
        "electric", "psychic" -> pokelightyellow
        "poison", "ghost" -> pokelightpurple
        "ground", "rock" -> pokelightbrown
        "dark" -> pokeblack
        else -> return pokelightblue
    }
}

