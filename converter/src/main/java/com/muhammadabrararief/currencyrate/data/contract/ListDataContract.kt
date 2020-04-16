package com.muhammadabrararief.currencyrate.data.contract

import com.muhammadabrararief.core.network.Outcome
import com.muhammadabrararief.currencyrate.data.model.resp.RatesResponse
import com.muhammadabrararief.currencyrate.list.Rate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface ListDataContract {

    interface Repository {
        val rateFetchOutcome: PublishSubject<Outcome<List<Rate>>>
        fun handleError(error: Throwable)
        fun fetchRates()
    }

    interface Local {
    }

    interface Remote {
        fun getRates(): Observable<RatesResponse>
    }

}