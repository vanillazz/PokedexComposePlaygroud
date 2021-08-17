package com.ardy.pokeappplayground.business.datasource.cache.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ardy.pokeappplayground.business.domain.model.Poke

@Entity(tableName = "pokemon")
data class PokemonEntity(
    //value for API
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    //value from API
    @ColumnInfo(name = "url")
    val url: String,

    //value from API
    @ColumnInfo(name = "name")
    val name: String,

    //value from API
    @ColumnInfo(name = "img_url")
    val imgUrl: String,

)

fun PokemonEntity.toPoke(): Poke {
    return Poke(
        id = id,
        url = url,
        name = name,
        imgUrl = imgUrl
    )
}