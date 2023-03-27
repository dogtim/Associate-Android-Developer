package com.example.associate.training.animation

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.associate.training.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class AnimationActivity : AppCompatActivity(), ContextCallback {

    private val viewModel: AnimationViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        viewModel.setCallback(this)
        setListener()
        startAnimation()
        grantPermission()
    }

    // Grant permission to write to external storage
    @RequiresApi(Build.VERSION_CODES.M)
    private fun grantPermission() {
        val permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        val grant = checkCallingOrSelfPermission(permission)
        if (grant != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(permission), 1)
        }
    }

    private fun setListener() {
        findViewById<AppCompatButton>(R.id.export_to_video).setOnClickListener {
            CoroutineScope(IO).launch {
                try {
                    viewModel.startEncoding()
                } catch (e: Exception) {
                    // Handle exception here
                }
            }
        }
    }

    private fun startAnimation() {
        val imageView: ImageView = findViewById(R.id.animation_img)

        val zoomIn = ObjectAnimator.ofPropertyValuesHolder(
            imageView,
            PropertyValuesHolder.ofFloat("scaleX", 1.0f, 3.0f),
            PropertyValuesHolder.ofFloat("scaleY", 1.0f, 3.0f)
        ).apply {
            duration = 1000 // 1 second
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }

        zoomIn.start()

    }

    override fun getBitmap(drawable: Int): Bitmap {
        return BitmapFactory.decodeResource(resources, drawable)
    }

    override fun getFile(): File {
        return File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "myvideo.mp4")
    }

}
