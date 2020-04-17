package com.muhammadabrararief.currencyrate.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.muhammadabrararief.core.constants.Constants
import com.muhammadabrararief.core.di.CoreComponent
import com.muhammadabrararief.core.network.Scheduler
import com.muhammadabrararief.currencyrate.data.ConverterService
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import com.muhammadabrararief.currencyrate.data.local.ConverterDb
import com.muhammadabrararief.currencyrate.data.local.ListLocalData
import com.muhammadabrararief.currencyrate.data.remote.ListRemoteData
import com.muhammadabrararief.currencyrate.data.repository.ListRepository
import com.muhammadabrararief.currencyrate.list.ListActivity
import com.muhammadabrararief.currencyrate.list.ListViewModelFactory
import com.muhammadabrararief.currencyrate.list.RatesAdapter
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

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
    fun listViewModelFactory(
        repository: ListDataContract.Repository,
        compositeDisposable: CompositeDisposable
    ): ListViewModelFactory = ListViewModelFactory(repository, compositeDisposable)

    /*Repository*/
    @Provides
    @ConverterScope
    fun listRepo(
        local: ListDataContract.Local,
        remote: ListDataContract.Remote,
        scheduler: Scheduler,
        compositeDisposable: CompositeDisposable
    ): ListDataContract.Repository = ListRepository(local, remote, scheduler, compositeDisposable)

    @Provides
    @ConverterScope
    fun remoteData(postService: ConverterService): ListDataContract.Remote =
        ListRemoteData(postService)

    @Provides
    @ConverterScope
    fun localData(postDb: ConverterDb, scheduler: Scheduler): ListDataContract.Local =
        ListLocalData(postDb, scheduler)

    @Provides
    @ConverterScope
    fun postDb(context: Context): ConverterDb =
        Room.databaseBuilder(
            context,
            ConverterDb::class.java,
            Constants.Converter.DB_NAME
        ).allowMainThreadQueries().build()

    @Provides
    @ConverterScope
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @ConverterScope
    fun postService(retrofit: Retrofit): ConverterService =
        retrofit.create(ConverterService::class.java)

}