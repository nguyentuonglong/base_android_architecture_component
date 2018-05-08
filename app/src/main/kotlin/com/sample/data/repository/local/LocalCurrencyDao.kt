package com.sample.data.repository.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface LocalCurrencyDao {

    @Query(LocalContract.SELECT_CURRENCIES_COUNT)
    fun getCurrenciesTotal(): Flowable<Int>

    @Insert
    fun insertAll(currencies: List<CurrencyEntity>)

    @Query(LocalContract.SELECT_CURRENCIES)
    fun getAllCurrencies(): Flowable<List<CurrencyEntity>>

}

