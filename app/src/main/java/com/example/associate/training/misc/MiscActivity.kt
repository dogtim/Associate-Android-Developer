package com.example.associate.training.misc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.associate.training.R
import com.example.associate.training.misc.fragment.MiscListFragment

class MiscActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFragment()
    }

    private fun createFragment() {
        setContentView(R.layout.activity_misc)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MiscListFragment>(R.id.canvas_fragment_container)
        }
    }

}

