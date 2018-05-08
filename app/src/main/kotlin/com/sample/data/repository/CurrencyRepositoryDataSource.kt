package com.sample.data.repository

import android.arch.lifecycle.LiveData
import com.sample.data.domain.AvailableExchange
import com.sample.data.domain.Currency
import io.reactivex.Flowable


interface CurrencyRepositoryDataSource {

    fun getTotalCurrencies(): Flowable<Int>

    fun addCurrencies()

    fun getCurrencyList(): LiveData<List<Currency>>

    fun getAvailableExchange(currencies: String): LiveData<AvailableExchange>

}
