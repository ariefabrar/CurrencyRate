package com.muhammadabrararief.currencyrate.data.repository

import android.os.Build
import androidx.lifecycle.Observer
import com.muhammadabrararief.core.testing.TestScheduler
import com.muhammadabrararief.currencyrate.data.contract.ListDataContract
import com.muhammadabrararief.currencyrate.data.model.resp.RatesResponse
import com.muhammadabrararief.currencyrate.list.Rate
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for [ListRepository]
 * */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class ListRepositoryTest {
    private val local: ListDataContract.Local = mock()
    private val remote: ListDataContract.Remote = mock()
    private val rates: Observer<List<Rate>> = mock()

    private lateinit var repository: ListRepository
    private val compositeDisposable = CompositeDisposable()

    @Before
    fun init() {
        repository = ListRepository(local, remote, TestScheduler(), compositeDisposable)
        whenever(remote.getRates()).doReturn(
            Observable.just(
                RatesResponse("EUR", mutableMapOf(Pair("AUD", 1.5), Pair("IDR", 10000.0)))
            )
        )
    }

    @Test
    fun `should call api then insert to db after call fetchRates`() {
        repository.fetchRates()
        verify(remote).getRates()
        verify(local).insertAllRates(any())
    }
}