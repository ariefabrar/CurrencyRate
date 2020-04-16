package com.muhammadabrararief.currencyrate.data.model.resp

data class RatesResponse(
    val baseCurrency: String,
    val rates: Map<String, Double>
)