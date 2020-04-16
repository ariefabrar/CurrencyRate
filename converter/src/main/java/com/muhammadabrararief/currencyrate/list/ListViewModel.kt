package com.muhammadabrararief.currencyrate.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhammadabrararief.currencyrate.common.ConverterDH
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.concurrent.schedule

class ListViewModel(
    private val repo: ListDataContract.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val finalRates = MediatorLiveData<List<Rate>>()
    private val rates: MutableLiveData<List<Rate>> = repo.rates
    private val baseRate: MutableLiveData<Rate> = MutableLiveData(Rate("EUR", 1.0))
    private val sortedRates: MutableLiveData<List<Rate>> = MutableLiveData()
    private lateinit var timer: Timer

    init {
        finalRates.addSource(rates) { finalRates.value = mixData(rates, baseRate, sortedRates) }
        finalRates.addSource(baseRate) { finalRates.value = mixData(rates, baseRate, sortedRates) }
        finalRates.addSource(sortedRates) { finalRates.value = mixData(rates, baseRate, sortedRates) }
    }

    private fun mixData(
        _rates: MutableLiveData<List<Rate>>,
        _baseRate: MutableLiveData<Rate>,
        _sortedRates: MutableLiveData<List<Rate>>
    ): List<Rate>? {
        val latestRates = _rates.value
        val newBaseRate = _baseRate.value
        val sortedRates = _sortedRates.value

        if (latestRates.isNullOrEmpty() || newBaseRate == null) return emptyList()
        if (sortedRates.isNullOrEmpty()) {
            this.sortedRates.value = latestRates
            return emptyList()
        }

        val inputAmount = newBaseRate.amount
        val inputCurr = newBaseRate.currencyCode
        val latestRatesMap: MutableMap<String, Double> = mutableMapOf()

        for (rate in latestRates) latestRatesMap[rate.currencyCode] = rate.amount

        val newBaseAmount = latestRatesMap[inputCurr] ?: 0.0

        return sortedRates.map { sortedRate ->
            val sortedAmount = latestRatesMap[sortedRate.currencyCode] ?: 0.0
            if (newBaseAmount == 0.0 || sortedAmount == 0.0) {
                Rate(sortedRate.currencyCode, 0.0)
            } else {
                Rate(sortedRate.currencyCode, inputAmount / newBaseAmount * sortedAmount)
            }
        }

    }

    fun getRates() {
        timer = Timer()
        timer.schedule(0, 1000) {
            repo.fetchRates()
        }
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
            sortedRates.value?.toMutableList()?.let {
                it.add(0, it.removeAt(position))
                sortedRates.value = it
            }
        }
    }

}