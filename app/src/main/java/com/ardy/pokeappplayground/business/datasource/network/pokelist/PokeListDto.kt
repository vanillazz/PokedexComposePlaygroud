package com.ardy.pokeappplayground.business.datasource.network.pokelist

import com.ardy.pokeappplayground.business.domain.model.Poke
import com.ardy.pokeappplayground.business.domain.model.urlToId
import com.ardy.pokeappplayground.util.Constants.Companion.POKE_THUMB_IMG_URL
import com.google.gson.annotations.SerializedName

data class PokeListDto(
    @SerializedName("name")
    var name: String?,

    @SerializedName("url")
    var url: String?,
)

fun PokeListDto.toPoke(): Poke {
    val imgName = (url?.let { urlToId(it) } ?: 0).toString().padStart(3, '0')
    return Poke(
        id = url?.let { urlToId(it) } ?: 0,
        url = url.orEmpty(),
        name = name.orEmpty(),
        imgUrl = POKE_THUMB_IMG_URL +imgName+".png"
    )
}

fun Poke.toDto(): PokeListDto {
    return PokeListDto(
        name = name,
        url = url
    )
}