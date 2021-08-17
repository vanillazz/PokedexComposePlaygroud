package com.ardy.pokeappplayground.business.datasource.network.pokedetail.response

import com.ardy.pokeappplayground.business.domain.model.CommonNameUrl
import com.google.gson.annotations.SerializedName

data class CommonNameUrlDto(
    @SerializedName("name") var name: String?,
    @SerializedName("url") var url: String?,
)


fun CommonNameUrlDto.toCommonNameUrl(): CommonNameUrl{
    return CommonNameUrl(
        name = name.orEmpty(),
        url = url.orEmpty()
    )
}