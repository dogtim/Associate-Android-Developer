package com.example.associate.training.lifecycle

import com.example.associate.training.Entry
import com.example.associate.training.MainActivity
import com.example.associate.training.lifecycle.aware.ChronoActivity3
import com.example.associate.training.lifecycle.aware.LocationActivity
import com.example.associate.training.lifecycle.aware.SavedStateActivity

class LifecycleActivity : MainActivity() {

    override fun getList(): List<Entry> {
        return listOf(
            Entry("ChronoActivity3", ChronoActivity3::class.java),
            Entry("LocationActivity", LocationActivity::class.java),
            Entry("SavedStateActivity", SavedStateActivity::class.java)
        )
    }
}