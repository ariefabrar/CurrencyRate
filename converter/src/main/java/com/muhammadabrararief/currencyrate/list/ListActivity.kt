package com.muhammadabrararief.currencyrate.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        viewModel.getRates()
        initDataObserver()
    }

    private fun initDataObserver() {
        //Observe the outcome and update state of the screen accordingly
//        viewModel.ratesOutcome.observe(this, Observer<Outcome<List<Rate>>> { outcome ->
//            when (outcome) {
//
//                is Outcome.Success -> {
//                    adapter.submitList(outcome.data.toMutableList())
//                }
//
//                is Outcome.Failure -> {
//                    outcome.e.printStackTrace()
//                    if (outcome.e is IOException)
//                        Toast.makeText(
//                            context,
//                            "Need Internet to fetch latest rates!",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    else
//                        Toast.makeText(
//                            context,
//                            "Failed to load posts. Please try again later.",
//                            Toast.LENGTH_LONG
//                        ).show()
//                }
//
//            }
//        })

        viewModel.rates.observe(this, Observer { t -> adapter.submitList(t) })
    }

    override fun onBaseRateChanged(position: Int, rate: Rate) {
        viewModel.setBaseRate(position, rate)
    }

}