package com.example.ontop_challenge

import android.app.Application
import com.example.ontop_challenge.di.networkModule
import com.example.ontop_challenge.di.repositoryModule
import com.example.ontop_challenge.di.useCaseModule
import com.example.ontop_challenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PokemonApplication)
            modules(
                networkModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
