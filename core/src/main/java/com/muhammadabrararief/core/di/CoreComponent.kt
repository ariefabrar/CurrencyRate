package com.muhammadabrararief.core.di

import android.content.Context
import com.muhammadabrararief.core.network.Scheduler
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(modules = [CoreModule::class, NetworkModule::class])
interface CoreComponent {

    fun context(): Context

    fun scheduler(): Scheduler

    fun retrofit(): Retrofit

}