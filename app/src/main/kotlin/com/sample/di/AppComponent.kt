package com.sample.di

import dagger.Component
import com.sample.feature.home.CurrencyViewModel
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, LocalModule::class, RemoteModule::class))
@Singleton
interface AppComponent {

    fun inject(currencyViewModel: CurrencyViewModel)

}


