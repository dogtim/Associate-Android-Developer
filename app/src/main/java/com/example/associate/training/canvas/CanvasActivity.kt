package com.example.associate.training.canvas

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

// Please refer to https://developer.android.com/reference/android/graphics/PorterDuff.Mode
class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(XfersModeView(this))
    }

}