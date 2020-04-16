package com.muhammadabrararief.currencyrate.data

import com.muhammadabrararief.currencyrate.data.model.resp.RatesResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ConverterService {
    @GET("latest?base=EUR")
    fun getCurrencyRates(): Observable<RatesResponse>
}