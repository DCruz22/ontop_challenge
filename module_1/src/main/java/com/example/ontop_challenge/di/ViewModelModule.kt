package com.example.ontop_challenge.di

import com.example.ontop_challenge.ui.viewmodel.PokemonDetailsViewModel
import com.example.ontop_challenge.ui.viewmodel.PokemonListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { Dispatchers }
    viewModel { PokemonListViewModel(get(), get()) }
    viewModel { PokemonDetailsViewModel(get(), get()) }
}
