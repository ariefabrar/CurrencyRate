package com.muhammadabrararief.currencyrate.data

import android.os.Build
import com.muhammadabrararief.core.testing.DependencyProvider
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

/**
 * Tests for [ConverterService]
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.O_MR1])
class ConverterServiceTest {
    private lateinit var converterService: ConverterService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        converterService = DependencyProvider
            .getRetrofit(mockWebServer.url("/"))
            .create(ConverterService::class.java)

    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getRates() {
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("rates"))
        }

        converterService
            .getCurrencyRates()
            .test()
            .run {
                assertNoErrors()
                assertValueCount(1)
                Assert.assertEquals(values()[0].rates.size, 31)
                Assert.assertEquals(values()[0].rates["IDR"], 16160.011)
                Assert.assertEquals(values()[0].baseCurrency, "EUR")
            }
    }

    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }
}