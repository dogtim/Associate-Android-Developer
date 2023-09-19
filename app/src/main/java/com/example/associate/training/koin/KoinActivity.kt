package com.example.associate.training.koin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.associate.training.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class KoinActivity : AppCompatActivity() {
    // Lazy Inject ViewModel
    val myViewModel: MyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_koin)
    }

    override fun onResume() {
        super.onResume()
        val textView: TextView = findViewById(R.id.koin_title)
        textView.text = myViewModel.sayHello()
    }
}

interface HelloRepository {
    fun giveHello(): String
}

class HelloRepositoryImpl() : HelloRepository {
    override fun giveHello() = "Hello Koin"
}

class MyViewModel(val repo : HelloRepository) : ViewModel() {

    fun sayHello() = "${repo.giveHello()} from $this"
}