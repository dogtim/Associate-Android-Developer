package com.example.associate.training.lifecycle

import com.example.associate.training.Entry
import com.example.associate.training.MainActivity
import com.example.associate.training.lifecycle.aware.ChronoActivity3

class LifecycleActivity : MainActivity() {

    override fun getList(): List<Entry> {
        return listOf(
            Entry("ChronoActivity3", ChronoActivity3::class.java)
        )
    }
}