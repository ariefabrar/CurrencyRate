package com.muhammadabrararief.currencyrate.data.remote

import com.muhammadabrararief.currencyrate.data.ConverterService
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import com.muhammadabrararief.currencyrate.data.model.resp.RatesResponse
import io.reactivex.Observable

class ListRemoteData(private val converterService: ConverterService) : ListDataContract.Remote {

    override fun getRates(): Observable<RatesResponse> = converterService.getCurrencyRates()

}