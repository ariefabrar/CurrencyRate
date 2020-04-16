package com.muhammadabrararief.currencyrate.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhammadabrararief.currencyrate.common.ConverterDH
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import io.reactivex.disposables.CompositeDisposable

class ListViewModel(
    private val repo: ListDataContract.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val finalRates = MediatorLiveData<List<Rate>>()
    private val rates: MutableLiveData<List<Rate>> = repo.rates
    private val baseRate: MutableLiveData<Rate> = MutableLiveData(Rate("EUR", 1.0))

    init {
        finalRates.addSource(rates) { finalRates.value = mixData(rates, baseRate) }
        finalRates.addSource(baseRate) { finalRates.value = mixData(rates, baseRate) }
    }

    private fun mixData(
        _rates: MutableLiveData<List<Rate>>,
        _baseRate: MutableLiveData<Rate>
    ): List<Rate>? {
        val rates = _rates.value
        val baseRate = _baseRate.value

        if (rates.isNullOrEmpty() || baseRate == null) return emptyList()

        return rates.map { rate -> Rate(rate.currencyCode, baseRate.amount * rate.amount) }

    }

    fun getRates() {
        repo.fetchRates()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        ConverterDH.destroyListComponent()
    }

    fun setBaseRate(position: Int, rate: Rate) {
        if (baseRate.value != rate) {
            baseRate.value = rate
        }

        if (position != 0) {
            rates.value?.toMutableList()?.let {
                it.add(0, it.removeAt(position))
                rates.value = it
            }
        }
    }

}