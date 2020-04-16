package com.muhammadabrararief.currencyrate.list

data class Rate(
    val currencyCode: String,
    val amount: Double
) {
    fun getFlagUrl() = "https://www.countryflags.io/${currencyCode.substring(0, 2)}/flat/64.png"
}