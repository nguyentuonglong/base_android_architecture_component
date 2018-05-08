package com.sample.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.sample.data.repository.remote.CurrencyResponse
import com.sample.data.repository.remote.RemoteCurrencyDataSource
import com.sample.data.repository.local.CurrencyEntity
import com.sample.data.repository.local.LocalCurrencyDataSource
import com.sample.data.domain.AvailableExchange
import com.sample.data.domain.Currency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
        private val localCurrencyDataSource: LocalCurrencyDataSource,
        private val remoteCurrencyDataSource: RemoteCurrencyDataSource
) : CurrencyRepositoryDataSource {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    override fun getTotalCurrencies() = localCurrencyDataSource.currencyDao().getCurrenciesTotal()

    override fun addCurrencies() {
        val currencyEntityList = LocalCurrencyDataSource.getAllCurrencies()
        localCurrencyDataSource.currencyDao().insertAll(currencyEntityList)
    }

    override fun getCurrencyList(): LiveData<List<Currency>> {
        val roomCurrencyDao = localCurrencyDataSource.currencyDao()
        val mutableLiveData = MutableLiveData<List<Currency>>()
        val disposable = roomCurrencyDao.getAllCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyList ->
                    mutableLiveData.value = transform(currencyList)
                }, { t: Throwable? -> t!!.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun transform(currencies: List<CurrencyEntity>): List<Currency> {
        val currencyList = ArrayList<Currency>()
        currencies.forEach {
            currencyList.add(Currency(it.countryCode, it.countryName))
        }
        return currencyList
    }

    override fun getAvailableExchange(currencies: String): LiveData<AvailableExchange> {
        val mutableLiveData = MutableLiveData<AvailableExchange>()
        val disposable = remoteCurrencyDataSource.requestAvailableExchange(currencies)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyResponse ->
                    if (currencyResponse.isSuccess) {
                        mutableLiveData.value = transform(currencyResponse)
                    } else {
                        throw Throwable("CurrencyRepository -> on Error occurred")
                    }
                }, { t: Throwable? -> t!!.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun transform(exchangeMap: CurrencyResponse): AvailableExchange {
        return AvailableExchange(exchangeMap.currencyQuotes)
    }

}
