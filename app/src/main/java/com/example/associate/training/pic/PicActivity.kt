package com.example.associate.training.pic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.associate.training.R

// The more complexity operation compare with the CanvasActivity
class PicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_pic)
        setContentView(SimpleDrawingView(this))
    }
}
