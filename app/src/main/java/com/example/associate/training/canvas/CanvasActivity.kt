package com.example.associate.training.canvas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.associate.training.R
import com.example.associate.training.canvas.view.CanvasListFragment

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFragment()
    }

    private fun createFragment() {
        setContentView(R.layout.activity_canvas)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CanvasListFragment>(R.id.canvas_fragment_container)
        }
    }

}

