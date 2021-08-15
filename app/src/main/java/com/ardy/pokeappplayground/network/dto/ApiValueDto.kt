package com.ardy.pokeappplayground.network.dto
import com.google.gson.annotations.SerializedName

data class ApiValueDto(
    @SerializedName("name") var name: String?,
    @SerializedName("url") var url: String?,
)