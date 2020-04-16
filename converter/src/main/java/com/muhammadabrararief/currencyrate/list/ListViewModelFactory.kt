package com.muhammadabrararief.currencyrate.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import io.reactivex.disposables.CompositeDisposable


class ListViewModelFactory(
    private val repository: ListDataContract.Repository,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ListViewModel(repository, compositeDisposable) as T

}