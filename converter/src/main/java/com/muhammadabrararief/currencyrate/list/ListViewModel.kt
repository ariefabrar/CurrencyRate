package com.muhammadabrararief.currencyrate.list

import androidx.lifecycle.ViewModel
import com.muhammadabrararief.currencyrate.common.ConverterDH

class ListViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        ConverterDH.destroyListComponent()
    }

}