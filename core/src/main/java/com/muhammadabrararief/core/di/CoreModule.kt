package com.muhammadabrararief.core.di

import android.content.Context
import com.muhammadabrararief.core.network.CoreScheduler
import com.muhammadabrararief.core.network.Scheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule(private val context: Context) {
    @Provides
    @Singleton
    fun providesContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun scheduler(): Scheduler {
        return CoreScheduler()
    }
}