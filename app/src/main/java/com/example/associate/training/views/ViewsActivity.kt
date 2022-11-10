package com.example.associate.training.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.associate.training.R
import com.example.associate.training.views.fragment.ViewsFragment

class ViewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFragment()
    }

    private fun createFragment() {
        setContentView(R.layout.activity_canvas)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<ViewsFragment>(R.id.canvas_fragment_container)
        }
    }

}