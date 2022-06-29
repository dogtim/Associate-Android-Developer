package com.example.associate.training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.associate.training.amphibian.AmphibianActivity
import com.example.associate.training.busschedule.BusScheduleActivity
import com.example.associate.training.databinding.ActivityMainBinding
import com.example.associate.training.dummy.DummyActivity
import com.example.associate.training.inventory.InventoryActivity
import com.example.associate.training.lifecycle.LifecycleActivity
import com.example.associate.training.word.WordActivity
import com.example.associate.training.workmanager.BlurWorkManagerActivity

open class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MainEntryAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = MainEntryAdapter(getList())
        binding.tutorsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.tutorsRecyclerView.adapter = adapter
    }

    open fun getList(): List<Entry> {
        return listOf(
            Entry("WorkManager", BlurWorkManagerActivity::class.java),
            Entry("ROOM Database \n sql", BusScheduleActivity::class.java),
            Entry("Amphibian \n Retrofit, Moshi, DataBinding", AmphibianActivity::class.java),
            Entry("WordActivity", WordActivity::class.java),
            Entry("DummyActivity", DummyActivity::class.java),
            Entry("InventoryActivity", InventoryActivity::class.java),
            Entry("LifecycleActivity", LifecycleActivity::class.java)
        )
    }
}