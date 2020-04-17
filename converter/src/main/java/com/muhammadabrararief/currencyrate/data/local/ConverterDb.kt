package com.muhammadabrararief.currencyrate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammadabrararief.currencyrate.list.Rate

@Database(entities = [Rate::class], version = 1, exportSchema = false)
abstract class ConverterDb : RoomDatabase() {
    abstract fun rateDao(): RateDao
}
