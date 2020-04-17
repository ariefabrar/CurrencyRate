package com.muhammadabrararief.currencyrate.data.contract

import androidx.lifecycle.LiveData
import com.muhammadabrararief.core.network.Outcome
import com.muhammadabrararief.currencyrate.data.model.resp.RatesResponse
import com.muhammadabrararief.currencyrate.list.Rate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface ListDataContract {

    interface Repository {
        val stateOutcome: PublishSubject<Outcome<String>>
        val rates: LiveData<List<Rate>>
        fun handleError(error: Throwable)
        fun fetchRates()
    }

    interface Local {
        fun getAllRates(): LiveData<List<Rate>>
        fun insertAllRates(rates: List<Rate>)
    }

    interface Remote {
        fun getRates(): Observable<RatesResponse>
    }

}