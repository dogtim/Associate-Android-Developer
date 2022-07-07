package com.example.associate.training.workmanager

import com.example.associate.training.Entry
import com.example.associate.training.MainActivity
import com.example.associate.training.lifecycle.aware.ChronoActivity3
import com.example.associate.training.lifecycle.aware.LocationActivity
import com.example.associate.training.lifecycle.aware.SavedStateActivity

class WorkManagerActivity : MainActivity() {

    override fun getList(): List<Entry> {
        return listOf(
            Entry("Fundamental", LocationActivity::class.java),
            Entry("Blur Image", BlurWorkManagerActivity::class.java)
        )
    }
}