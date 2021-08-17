package com.ardy.pokeappplayground.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ardy.pokeappplayground.BuildConfig
import com.ardy.pokeappplayground.business.datasource.cache.database.AppDatabase
import com.ardy.pokeappplayground.business.datasource.cache.database.AppDatabase.Companion.DATABASE_NAME
import com.ardy.pokeappplayground.business.datasource.cache.database.dao.PokemonDao
import com.ardy.pokeappplayground.presentation.BaseApplication
import com.ardy.pokeappplayground.util.Constants
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication{
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun providePokemonDao(db: AppDatabase): PokemonDao{
        return db.pokemonDao()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext app: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(ChuckerInterceptor(app))
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson, okHttpClient: OkHttpClient): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }
}