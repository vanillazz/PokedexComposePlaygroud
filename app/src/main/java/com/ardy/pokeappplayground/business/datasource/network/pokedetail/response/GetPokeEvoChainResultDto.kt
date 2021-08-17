package com.ardy.pokeappplayground.business.datasource.network.pokedetail.response

import com.ardy.pokeappplayground.business.datasource.network.pokedetail.response.CommonNameUrlDto
import com.ardy.pokeappplayground.business.domain.model.PokeEvoChain
import com.google.gson.annotations.SerializedName

data class GetPokeEvoChainResultDto(
    @SerializedName("chain") var chain: PokeEvoChainDto?,
)

data class PokeEvoChainDto(
    @SerializedName("species") var species: CommonNameUrlDto?,
    @SerializedName("evolves_to") var evolvesTo: List<PokeEvoChainDto>?,
)

fun GetPokeEvoChainResultDto.toPokeEvoChain(): PokeEvoChain {

    var temp = ArrayList<PokeEvoChain>()
    temp.add(PokeEvoChain(chain?.species?.name.orEmpty(), chain?.species?.url.orEmpty()))


    var xx = chain?.evolvesTo?.flatMap { it.evolvesTo.orEmpty() }?.map { it.species }

//    val evolveChain: PokeEvoChainDto = chain?.evolvesTo?.first()?.unboxEvolveData()
//    evolveChain?.let {
//        temp.add(PokeEvoChain(it.species?.name.orEmpty(), it.species?.url.orEmpty()))
//        evolveChain.unboxEvolveData()
//    }


    return PokeEvoChain(
        name = "TEST",
        speciesUrl = "TEST"
    )
}

fun PokeEvoChainDto.unboxEvolveData(): PokeEvoChainDto? {
    if (evolvesTo?.isNotEmpty() == true) {
        return evolvesTo!!.first()
    } else {
        return null
    }
}

//"species": {
//    "name": "charmender",
//    "url": "https://pokeapi.co/api/v2/pokemon-species/5/"
//},
//"evolves_to": [
//    {
//        "is_baby": false,
//        "species": {
//            "name": "charmeleon",
//            "url": "https://pokeapi.co/api/v2/pokemon-species/5/"
//        },
//        "evolves_to": [
//            {
//                "is_baby": false,
//                "species": {
//                    "name": "charizard",
//                    "url": "https://pokeapi.co/api/v2/pokemon-species/6/"
//                },
//                "evolves_to": []
//            }
//        ],
//    }
//],