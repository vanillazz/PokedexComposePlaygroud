package com.ardy.pokeappplayground.business.datasource.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ardy.pokeappplayground.business.datasource.cache.database.entity.PokemonEntity
import com.ardy.pokeappplayground.util.Constants.Companion.PAGINATION_PAGE_SIZE

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>): LongArray

    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemons()

    @Query("""
        SELECT * FROM pokemon 
        LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """)
    suspend fun getAllPokemons(
        page: Int,
        pageSize: Int = PAGINATION_PAGE_SIZE
    ): List<PokemonEntity>
}