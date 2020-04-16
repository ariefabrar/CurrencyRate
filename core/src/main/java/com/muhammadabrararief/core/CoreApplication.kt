package com.muhammadabrararief.core

import android.app.Application
import com.muhammadabrararief.core.di.CoreComponent
import com.muhammadabrararief.core.di.CoreModule
import com.muhammadabrararief.core.di.DaggerCoreComponent

open class CoreApplication : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().coreModule(CoreModule(this)).build()
    }
}