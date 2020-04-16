package com.muhammadabrararief.currencyrate.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.muhammadabrararief.core.ext.toLiveData
import com.muhammadabrararief.core.network.Outcome
import com.muhammadabrararief.currencyrate.common.ConverterDH
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import io.reactivex.disposables.CompositeDisposable

class ListViewModel(
    private val repo: ListDataContract.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val ratesOutcome: LiveData<Outcome<List<Rate>>> by lazy {
        repo.rateFetchOutcome.toLiveData(compositeDisposable)
    }

    fun getRates() {
        repo.fetchRates()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        ConverterDH.destroyListComponent()
    }

}