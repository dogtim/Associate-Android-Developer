package com.example.associate.training.media

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
import com.example.associate.training.databinding.ActivityMediaBinding
import java.io.File
import java.util.*

class MediaActivity : AppCompatActivity(), ContextCallback {

    private val viewModel: MediaViewModel by viewModels()
    private lateinit var binding: ActivityMediaBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setListener()
        startAnimation()
        grantPermission()
    }

    private fun setupViewModel() {
        viewModel.setCallback(this)
        viewModel.state.observe(this) { state ->
            when (state) {
                is EncodingState.Init -> showToast("Init")
                is EncodingState.Loading -> showLoading()
                is EncodingState.Complete -> showToast("Complete")
            }
        }
    }

    private fun showLoading() {
        binding.progressIndicator.isIndeterminate = true
    }

    private fun showToast(text: String) {
        binding.progressIndicator.isIndeterminate = false
        binding.content.text = text
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
        binding.exportToVideo.setOnClickListener {
            viewModel.startEncoding()
        }
    }

    private fun startAnimation() {
        val imageView: ImageView = binding.animationImg

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
