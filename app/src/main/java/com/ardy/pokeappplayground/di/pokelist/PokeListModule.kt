package com.ardy.pokeappplayground.di.pokelist

import com.ardy.pokeappplayground.business.datasource.cache.database.dao.PokemonDao
import com.ardy.pokeappplayground.business.datasource.network.PokeListApiService
import com.ardy.pokeappplayground.business.interactors.pokelist.GetPokemon
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PokeListModule {

    @Singleton
    @Provides
    fun providePokeListApiService(retrofitBuilder: Retrofit.Builder): PokeListApiService {
        return retrofitBuilder
            .build()
            .create(PokeListApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetPokemon(
        dao: PokemonDao,
        service: PokeListApiService
    ): GetPokemon {
        return GetPokemon(
            cache = dao,
            service = service
        )
    }

}