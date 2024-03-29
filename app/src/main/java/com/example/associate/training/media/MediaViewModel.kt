package com.example.associate.training.media

import android.graphics.*
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import android.util.Log
import android.view.Surface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.associate.training.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

interface ContextCallback {
    fun getBitmap(drawable: Int): Bitmap
    fun getFile(): File
}

sealed class EncodingState {
    object Init : EncodingState()
    object Loading : EncodingState()
    object Complete : EncodingState()
}
class MediaViewModel(private val height: Int, private val width: Int) : ViewModel() {
    private val TAG = this::class.simpleName
    private var videoTrackIndex: Int = 0
    private var callback: ContextCallback? = null
    private val _state = MutableLiveData<EncodingState>()
    val state: LiveData<EncodingState> = _state

    fun setCallback(callback: ContextCallback) {
        this.callback = callback
    }

    // Encode the zoomIn animation for 5 durations
    fun startEncoding() {
        if (_state.value == EncodingState.Loading) return
        _state.postValue(EncodingState.Loading)
        Log.i("Tim", "height $height, width $width")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bitrate = 3000000
                val mime = "video/avc"
                val format = MediaFormat.createVideoFormat(mime, width, height)
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
                val videoPath = createTempFile()
                val muxer = MediaMuxer(videoPath.path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
                val bufferInfo = MediaCodec.BufferInfo()

                encode(surface, bufferInfo, codec, muxer)
                // Release resources
                codec.stop()
                codec.release()
                muxer.stop()
                muxer.release()
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            } finally {
                _state.postValue(EncodingState.Complete)
            }
        }
    }

    private fun renderBitmap(drawable: Int, surface: Surface) {
        callback?.let {
            val bitmap = it.getBitmap(drawable)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            val canvas: Canvas = surface.lockCanvas(null)
            canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR)
            canvas.drawBitmap(scaledBitmap, 0.0f, 0.0f, null)
            surface.unlockCanvasAndPost(canvas)
        }
    }

    private fun encode(
        surface: Surface,
        bufferInfo: MediaCodec.BufferInfo,
        encoder: MediaCodec,
        muxer: MediaMuxer
    ) {
        val dequeueTimeoutUsec = 10000L
        val videoDuration = 5 * 1000000L
        var startTime: Long = -1
        var isEndOfStream = false
        val drawableList = listOf(R.drawable.birthday, R.drawable.birthday_cakes, R.drawable.happy_birthday)
        var index = 0
        while (true) {
            renderBitmap(drawableList[index], surface)
            val outputBufferIndex = encoder.dequeueOutputBuffer(bufferInfo, dequeueTimeoutUsec)
            val presentationTimeUs = bufferInfo.presentationTimeUs
            if (startTime == -1L && presentationTimeUs > 0) {
                startTime = presentationTimeUs
            } else if (startTime != -1L) {
                if (presentationTimeUs - startTime > videoDuration && !isEndOfStream) {
                    encoder.signalEndOfInputStream()
                    isEndOfStream = true
                } else {
                    index = (presentationTimeUs / 1000000L).toInt() % drawableList.size
                }
            }
            if (outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                Log.i(TAG, "no output available, spinning to await EOS")
                //break
            } else if (outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                Log.i(TAG, "start muxer")
                videoTrackIndex = muxer.addTrack(encoder.outputFormat)
                muxer.start()
            } else if (outputBufferIndex < 0) {
                Log.i(TAG, "unexpected result from encoder.dequeueOutputBuffer")
            } else {
                val encodedDataBuffer = encoder.getOutputBuffer(outputBufferIndex)
                    ?: throw RuntimeException("encoderOutputBuffer $outputBufferIndex was null")

                if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
                    Log.i(TAG, "ignoring BUFFER_FLAG_CODEC_CONFIG")
                    bufferInfo.size = 0
                }

                if (bufferInfo.size != 0) {
                    // adjust the ByteBuffer values to match BufferInfo
                    encodedDataBuffer.position(bufferInfo.offset)
                    encodedDataBuffer.limit(bufferInfo.offset + bufferInfo.size)
                    muxer.writeSampleData(videoTrackIndex, encodedDataBuffer, bufferInfo)
                }

                encoder.releaseOutputBuffer(outputBufferIndex, false)

                if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                    Log.i(TAG, "Get EOS")
                    break
                }
            }
        }
    }

    private fun createTempFile(): File {

        callback?.let {
            val file = it.getFile()
            Log.i(TAG, "file: ${file.absolutePath}")
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            return file
        }

        throw RuntimeException("file not found")
    }
}