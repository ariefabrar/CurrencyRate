package com.muhammadabrararief.currencyrate.common

import com.muhammadabrararief.core.CoreApplication
import com.muhammadabrararief.currencyrate.di.DaggerListComponent
import com.muhammadabrararief.currencyrate.di.ListComponent
import javax.inject.Singleton

@Singleton
object ConverterDH {
    private var listComponent: ListComponent? = null

    fun listComponent(): ListComponent {
        if (listComponent == null)
            listComponent =
                DaggerListComponent.builder().coreComponent(CoreApplication.coreComponent).build()
        return listComponent as ListComponent
    }

    fun destroyListComponent() {
        listComponent = null
    }
}