package com.example.ontop_challenge.di

import com.example.ontop_challenge.domain.usecase.GetPokemonDetailsUseCase
import com.example.ontop_challenge.domain.usecase.GetPokemonListUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single {
        GetPokemonListUseCase(get())
    }
    single {
        GetPokemonDetailsUseCase(get())
    }
}
