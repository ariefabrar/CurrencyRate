package com.muhammadabrararief.currencyrate.list

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.muhammadabrararief.core.base.CoreActivity
import com.muhammadabrararief.currencyrate.R
import com.muhammadabrararief.currencyrate.common.ConverterDH
import kotlinx.android.synthetic.main.activity_list.*
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
        viewModel.getRates()
        initDataObserver()
    }

    private fun initDataObserver() {
        viewModel.rates.observe(this, Observer { t -> adapter.submitList(t) })
    }

    override fun onBaseRateChanged(position: Int, rate: Rate) {
        viewModel.setBaseRate(position, rate)
    }

}