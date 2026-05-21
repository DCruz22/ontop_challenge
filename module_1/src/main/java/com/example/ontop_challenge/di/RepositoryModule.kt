package com.example.ontop_challenge.di

import com.example.ontop_challenge.domain.repository.PokemonRepositoryImpl
import com.example.ontop_challenge.data.repository.PokemonRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<PokemonRepository> {
        PokemonRepositoryImpl(get())
    }
}
