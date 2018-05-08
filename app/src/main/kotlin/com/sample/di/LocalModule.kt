package com.sample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import com.sample.data.repository.local.LocalCurrencyDataSource
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context) =
            LocalCurrencyDataSource.buildPersistentCurrency(context)
}


