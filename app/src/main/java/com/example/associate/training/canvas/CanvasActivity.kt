package com.example.associate.training.canvas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// Please refer to https://developer.android.com/reference/android/graphics/PorterDuff.Mode
class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(XfersModeView(this))
         setContentView(CircularAvatarView(this))
        // setContentView(SaveLayerView(this))

    }

}