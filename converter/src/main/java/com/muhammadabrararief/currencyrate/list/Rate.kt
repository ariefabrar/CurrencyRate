package com.muhammadabrararief.currencyrate.list

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rate(
    @PrimaryKey val currencyCode: String,
    val amount: Double
) {
    fun getFlagUrl() = "https://www.countryflags.io/${currencyCode.substring(0, 2)}/flat/64.png"
}