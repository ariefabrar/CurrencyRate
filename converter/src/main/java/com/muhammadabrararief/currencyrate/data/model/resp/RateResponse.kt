package com.muhammadabrararief.currencyrate.data.model.resp

import androidx.annotation.Keep

@Keep
data class RatesResponse(
    val baseCurrency: String,
    val rates: Map<String, Double>
)