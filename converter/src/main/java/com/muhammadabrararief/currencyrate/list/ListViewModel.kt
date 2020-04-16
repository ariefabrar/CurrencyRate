package com.muhammadabrararief.currencyrate.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muhammadabrararief.currencyrate.common.ConverterDH
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import io.reactivex.disposables.CompositeDisposable

class ListViewModel(
    private val repo: ListDataContract.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val rates: MutableLiveData<List<Rate>> = repo.rates

    fun getRates() {
        repo.fetchRates()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        ConverterDH.destroyListComponent()
    }

    fun setBaseRate(position: Int, rate: Rate) {
        rates.value?.toMutableList()?.let {
            it.add(0, it.removeAt(position))
            rates.value = it
        }
    }

}