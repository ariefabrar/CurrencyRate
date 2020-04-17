package  com.muhammadabrararief.currencyrate.data.remote

import android.os.Build
import com.muhammadabrararief.currencyrate.data.ConverterService
import com.muhammadabrararief.currencyrate.data.model.resp.RatesResponse
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for [ListRemoteData]
 */

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class ListRemoteDataTest {

    private val converterService = mock<ConverterService>()

    @Test
    fun getRates() {
        whenever(converterService.getCurrencyRates()).thenReturn(
            Observable.just(
                RatesResponse("EUR", mutableMapOf(Pair("AUD", 1.5), Pair("IDR", 10000.0)))
            )
        )

        ListRemoteData(converterService).getRates().test().run {
            assertNoErrors()
            assertValueCount(1)
            assertEquals(values()[0].rates.size, 2)
            assertEquals(values()[0].baseCurrency, "EUR")

        }
    }

}