package com.muhammadabrararief.currencyrate.list

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.muhammadabrararief.core.base.CoreActivity
import com.muhammadabrararief.core.network.Outcome
import com.muhammadabrararief.currencyrate.R
import com.muhammadabrararief.currencyrate.common.ConverterDH
import kotlinx.android.synthetic.main.activity_list.*
import java.io.IOException
import javax.inject.Inject

class ListActivity : CoreActivity(), RatesAdapter.Listener {

    private val component by lazy { ConverterDH.listComponent() }

    @Inject
    lateinit var viewModelFactory: ListViewModelFactory

    @Inject
    lateinit var adapter: RatesAdapter

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)
    }

    private val context: Context by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        component.inject(this)
        adapter.listener = this
        rvRates.adapter = adapter
        rvRates.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getRates()
                } else {
                    viewModel.stopPolling()
                }
            }
        })
        initDataObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRates()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPolling()
    }

    private fun initDataObserver() {
        viewModel.finalRates.observe(this, Observer { t -> adapter.submitList(t) })

        val snackbar = Snackbar.make(rvRates, R.string.try_again, Snackbar.LENGTH_SHORT)
        viewModel.stateOutcome.observe(this, Observer<Outcome<String>> { outcome ->
            when (outcome) {
                is Outcome.Progress -> {
                }
                is Outcome.Success -> {
                    snackbar.dismiss()
                }
                is Outcome.Failure -> {
                    if (outcome.e is IOException) {
                        snackbar.setText(R.string.need_internet).show()
                    } else {
                        snackbar.show()
                    }
                }

            }
        })
    }

    override fun onBaseRateChanged(position: Int, rate: Rate) {
        viewModel.setBaseRate(position, rate)
    }

}