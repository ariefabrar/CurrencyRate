package com.muhammadabrararief.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class CoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()

    }

    private fun setUpToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}