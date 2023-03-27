package com.example.associate.training.animation

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.*
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Surface
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.associate.training.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class AnimationActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        setListener()
        startAnimation()
        grantPermision()
    }

    // Grant permission to write to external storage
    @RequiresApi(Build.VERSION_CODES.M)
    private fun grantPermision() {
        val permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        val grant = checkCallingOrSelfPermission(permission)
        if (grant != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(permission), 1)
        }
    }

    private var videoTrackIndex: Int = 0

    // Encode the zoomIn animation for 5 durations
    private fun startEncoding() {
        val size = 720
        val bitrate = 2000000
        val mime = "video/avc"
        val format = MediaFormat.createVideoFormat(mime, size, size)
        format.setInteger(MediaFormat.KEY_BIT_RATE, bitrate)
        format.setInteger(MediaFormat.KEY_FRAME_RATE, 30)
        format.setInteger(
            MediaFormat.KEY_COLOR_FORMAT,
            MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
        )
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)
        val codec = MediaCodec.createEncoderByType(mime)
        codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        val surface: Surface = codec.createInputSurface()
        codec.start()

        // Create MediaMuxer and add track
        val VIDEO_PATH = createTempFile()

        val muxer = MediaMuxer(VIDEO_PATH.path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        val bufferInfo = MediaCodec.BufferInfo()

        encode(surface, bufferInfo, codec, muxer, size)
        // Release resources
        codec.stop()
        codec.release()
        muxer.stop()
        muxer.release()
    }

    private fun renderBitmap(drawable: Int, surface: Surface, size: Int) {
        val bitmap = BitmapFactory.decodeResource(resources, drawable)
        val desiredHeight = (bitmap.height.toFloat() / bitmap.width.toFloat() * size).toInt()
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, desiredHeight, true)
        val canvas: Canvas = surface.lockCanvas(null)
        canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR)
        canvas.drawBitmap(scaledBitmap, 0.0f, 0.0f, null)
        surface.unlockCanvasAndPost(canvas)
    }
    private fun encode(
        surface: Surface,
        mBufferInfo: MediaCodec.BufferInfo,
        encoder: MediaCodec,
        muxer: MediaMuxer,
        size: Int
    ) {
        val DEQUEUE_TIMEOUT_USEC = 10000L
        val videoDuration = 5 * 1000000L
        var startTime: Long = -1
        var isEndOfStream = false
        val drawableList = listOf(R.drawable.birthday, R.drawable.birthday_cakes, R.drawable.happy_birthday)
        var index = 0
        while (true) {
            renderBitmap(drawableList[index], surface, size)
            val outputBufferIndex = encoder.dequeueOutputBuffer(mBufferInfo, DEQUEUE_TIMEOUT_USEC)
            val presentationTimeUs = mBufferInfo.presentationTimeUs
            if (startTime == -1L && presentationTimeUs > 0) {
                startTime = presentationTimeUs
            } else if (startTime != -1L) {
                if (presentationTimeUs - startTime > videoDuration && !isEndOfStream) {
                    encoder.signalEndOfInputStream()
                    isEndOfStream = true
                } else {
                    index = (presentationTimeUs / 1000000L).toInt() % drawableList.size
                    Log.i("Tim", "index $index")
                }
            }
            if (outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                Log.i("Tim", "no output available, spinning to await EOS")
                //break
            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                Log.i("Tim", "start muxer")
                videoTrackIndex = muxer.addTrack(encoder.outputFormat)
                muxer.start()
            } else if (outputBufferIndex < 0) {
                Log.i("Tim", "unexpected result from encoder.dequeueOutputBuffer")
            } else {
                val encodedDataBuffer = encoder.getOutputBuffer(outputBufferIndex)
                    ?: throw RuntimeException("encoderOutputBuffer $outputBufferIndex was null")

                if (mBufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
                    Log.i("Tim", "ignoring BUFFER_FLAG_CODEC_CONFIG")
                    mBufferInfo.size = 0
                }

                if (mBufferInfo.size != 0) {
                    // adjust the ByteBuffer values to match BufferInfo
                    encodedDataBuffer.position(mBufferInfo.offset)
                    encodedDataBuffer.limit(mBufferInfo.offset + mBufferInfo.size)
                    muxer.writeSampleData(videoTrackIndex, encodedDataBuffer, mBufferInfo)
                }

                encoder.releaseOutputBuffer(outputBufferIndex, false)

                if (mBufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                    Log.i("Tim", "get EOS")
                    break
                }
            }
        }
    }

    private fun setListener() {
        findViewById<AppCompatButton>(R.id.export_to_video).setOnClickListener {
            CoroutineScope(IO).launch {
                try {
                    startEncoding()
                } catch (e: Exception) {
                    // Handle exception here
                }
            }
        }
    }

    private fun createTempFile(): File {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "myvideo.mp4")
        Log.i("Tim", "file: ${file.absolutePath}")
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return file
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

}
