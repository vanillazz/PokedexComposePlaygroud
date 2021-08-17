package com.ardy.pokeappplayground.di.pokedetail

import com.ardy.pokeappplayground.business.datasource.network.PokeDetailApiService
import com.ardy.pokeappplayground.business.interactors.pokedetail.GetPokemonDetail
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PokeDetailModule {

    @Singleton
    @Provides
    fun providePokeDetailApiService(retrofitBuilder: Retrofit.Builder): PokeDetailApiService {
        return retrofitBuilder
            .build()
            .create(PokeDetailApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGetPokemonDetail(
//        pokeDao: PokeDao,
        service: PokeDetailApiService
    ): GetPokemonDetail {
        return GetPokemonDetail(
            service = service
        )
    }

}