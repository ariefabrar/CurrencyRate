package com.muhammadabrararief.currencyrate.di

import android.content.Context
import com.bumptech.glide.Glide
import com.muhammadabrararief.core.di.CoreComponent
import com.muhammadabrararief.currencyrate.list.ListActivity
import com.muhammadabrararief.currencyrate.list.ListViewModelFactory
import com.muhammadabrararief.currencyrate.list.RatesAdapter
import dagger.Component
import dagger.Module
import dagger.Provides

@ConverterScope
@Component(dependencies = [CoreComponent::class], modules = [ListModule::class])
interface ListComponent {

    fun inject(listActivity: ListActivity)
}

@Module
class ListModule {

    /*Adapter*/
    @Provides
    @ConverterScope
    fun adapter(context: Context): RatesAdapter = RatesAdapter(Glide.with(context))

    /*ViewModel*/
    @Provides
    @ConverterScope
    fun listViewModelFactory(): ListViewModelFactory = ListViewModelFactory()

}