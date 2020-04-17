package com.muhammadabrararief.currencyrate.common

import com.muhammadabrararief.core.CoreApplication
import com.muhammadabrararief.currencyrate.di.ConverterComponent
import com.muhammadabrararief.currencyrate.di.DaggerConverterComponent
import javax.inject.Singleton

@Singleton
object ConverterDH {
    private var converterComponent: ConverterComponent? = null

    fun listComponent(): ConverterComponent {
        if (converterComponent == null)
            converterComponent =
                DaggerConverterComponent.builder().coreComponent(CoreApplication.coreComponent).build()
        return converterComponent as ConverterComponent
    }

    fun destroyListComponent() {
        converterComponent = null
    }
}