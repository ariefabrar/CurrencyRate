package com.muhammadabrararief.currencyrate.data.repository

import androidx.lifecycle.LiveData
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
    private val local: ListDataContract.Local,
    private val remote: ListDataContract.Remote,
    private val scheduler: Scheduler,
    private val compositeDisposable: CompositeDisposable
) : ListDataContract.Repository {

    override val stateOutcome: PublishSubject<Outcome<String>> =
        PublishSubject.create<Outcome<String>>()

    override val rates: LiveData<List<Rate>> = local.getAllRates()

    override fun fetchRates() {
        remote.getRates()
            .performOnBackOutOnMain(scheduler)
            .subscribe({ response ->
                val items: MutableList<Rate> = mutableListOf()
                items.add(Rate(response.baseCurrency, 1.0))
                items.addAll(response.rates.map { entry ->
                    Rate(entry.key, entry.value)
                })
                stateOutcome.success("SUCCESS")
                local.insertAllRates(items)
            }, { t -> stateOutcome.failed(t) })
            .addTo(compositeDisposable)
    }

    override fun handleError(error: Throwable) {
        stateOutcome.failed(error)
    }
}