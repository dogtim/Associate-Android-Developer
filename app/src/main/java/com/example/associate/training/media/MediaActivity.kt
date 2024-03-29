package com.example.associate.training.media

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.associate.training.databinding.ActivityMediaBinding
import org.koin.core.parameter.parametersOf
import org.koin.android.ext.android.inject
import java.io.File
import java.util.*

class MediaActivity : AppCompatActivity(), ContextCallback {

    private lateinit var binding: ActivityMediaBinding
    val viewModel: MediaViewModel by inject { parametersOf(720, 1280) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setListener()
        grantPermission()
    }

    private fun setupViewModel() {
        // Use the ViewModelProvider to get an instance of MediaViewModel
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

    override fun getBitmap(drawable: Int): Bitmap {
        return BitmapFactory.decodeResource(resources, drawable)
    }

    override fun getFile(): File {
        return File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "myvideo.mp4")
    }

}
