package com.muhammadabrararief.currencyrate.list

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.muhammadabrararief.core.base.CoreActivity
import com.muhammadabrararief.currencyrate.R
import com.muhammadabrararief.currencyrate.common.ConverterDH
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ListActivity : CoreActivity() {

    private val TAG = "ListActivity"

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
        rvRates.adapter = adapter
    }

}