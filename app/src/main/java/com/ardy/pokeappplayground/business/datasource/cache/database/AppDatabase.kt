package com.ardy.pokeappplayground.business.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ardy.pokeappplayground.business.datasource.cache.database.dao.PokemonDao
import com.ardy.pokeappplayground.business.datasource.cache.database.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){

    abstract fun pokemonDao(): PokemonDao

    companion object{
        val DATABASE_NAME: String = "pokemon_db"
    }
}