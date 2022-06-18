package com.example.associate.training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.associate.training.databinding.ActivityMainBinding
import com.example.associate.training.dummynetwork.DummyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MainEntryAdapter
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        initNetwork()
    }

    private fun setupAdapter() {
        adapter = MainEntryAdapter()
        binding.tutorsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.tutorsRecyclerView.adapter = adapter
    }

    private fun initNetwork() {
        lifecycleScope.launch(Dispatchers.IO) {
            val repository = DummyRepository()
            val result = repository.makeLoginRequest()

            Log.i("dogtim", "result " + result.toString())
        }

    }
}