package com.muhammadabrararief.currencyrate.data.repository

import androidx.lifecycle.MutableLiveData
import com.muhammadabrararief.core.ext.addTo
import com.muhammadabrararief.core.ext.failed
import com.muhammadabrararief.core.ext.performOnBackOutOnMain
import com.muhammadabrararief.core.ext.success
import com.muhammadabrararief.core.network.Outcome
import com.muhammadabrararief.core.network.Scheduler
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import com.muhammadabrararief.currencyrate.list.Rate
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class ListRepository(
    private val remote: ListDataContract.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : ListDataContract.Repository {

    override val rateFetchOutcome: PublishSubject<Outcome<List<Rate>>> =
        PublishSubject.create<Outcome<List<Rate>>>()

    private val tempRates: MutableLiveData<List<Rate>> = MutableLiveData()
    override val rates: MutableLiveData<List<Rate>> = tempRates


    override fun fetchRates() {
        remote.getRates()
            .performOnBackOutOnMain(scheduler)
            .subscribe({ response ->
                val items: MutableList<Rate> = mutableListOf()
                items.add(Rate(response.baseCurrency, 1.0))
                items.addAll(response.rates.map { entry ->
                    Rate(entry.key, entry.value)
                })
                rateFetchOutcome.success(items)
                tempRates.value = items
            }, { t -> rateFetchOutcome.failed(t) })
            .addTo(compositeDisposable)
    }

    override fun handleError(error: Throwable) {
        rateFetchOutcome.failed(error)
    }
}