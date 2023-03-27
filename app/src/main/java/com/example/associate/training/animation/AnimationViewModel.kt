package com.example.associate.training.animation

import android.graphics.*
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import android.util.Log
import android.view.Surface
import androidx.lifecycle.ViewModel
import com.example.associate.training.R
import java.io.File

interface ContextCallback {
    fun getBitmap(drawable: Int): Bitmap
    fun getFile(): File
}

class AnimationViewModel : ViewModel() {

    private var videoTrackIndex: Int = 0
    private var callback: ContextCallback? = null

    fun setCallback(callback: ContextCallback) {
        this.callback = callback
    }

    // Encode the zoomIn animation for 5 durations
    fun startEncoding() {
        val size = 720
        val bitrate = 3000000
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
        callback?.let {
            val bitmap = it.getBitmap(drawable)
            val desiredHeight = (bitmap.height.toFloat() / bitmap.width.toFloat() * size).toInt()
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, size, desiredHeight, true)
            val canvas: Canvas = surface.lockCanvas(null)
            canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR)
            canvas.drawBitmap(scaledBitmap, 0.0f, 0.0f, null)
            surface.unlockCanvasAndPost(canvas)
        }
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

    private fun createTempFile(): File {

        callback?.let {
            val file = it.getFile()
            Log.i("Tim", "file: ${file.absolutePath}")
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            return file
        }

        throw RuntimeException("file not found")
    }
}