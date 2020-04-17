package com.muhammadabrararief.currencyrate.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammadabrararief.currencyrate.list.Rate

@Dao
interface RateDao {
    @Query("SELECT * FROM rate")
    fun getAllRates(): LiveData<List<Rate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRates(rates: List<Rate>)
}