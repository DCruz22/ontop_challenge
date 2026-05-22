package com.example.ontop_challenge.di

import com.example.ontop_challenge.data.datasource.PokemonApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder().build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<PokemonApiService> {
        get<Retrofit>().create(PokemonApiService::class.java)
    }
}
