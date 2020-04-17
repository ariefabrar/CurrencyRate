package com.muhammadabrararief.currencyrate.data.local

import androidx.lifecycle.LiveData
import com.muhammadabrararief.core.ext.performOnBack
import com.muhammadabrararief.core.network.Scheduler
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import com.muhammadabrararief.currencyrate.list.Rate
import io.reactivex.Completable


class ListLocalData(private val converterDb: ConverterDb, private val scheduler: Scheduler) :
    ListDataContract.Local {

    override fun getAllRates(): LiveData<List<Rate>> {
        return converterDb.rateDao().getAllRates()
    }

    override fun insertAllRates(rates: List<Rate>) {
        Completable.fromAction {
            converterDb.rateDao().insertAllRates(rates)
        }
            .performOnBack(scheduler)
            .subscribe()

    }

}