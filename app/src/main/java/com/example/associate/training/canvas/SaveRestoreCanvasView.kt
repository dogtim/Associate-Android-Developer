package com.example.associate.training.canvas

import com.example.associate.training.R
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

// https://www.twle.cn/l/yufei/android/android-basic-canvas-savelayer.html
class SaveRestoreCanvasView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    AppCompatImageView(context!!, attrs) {
    private val mBitmap: Bitmap?
    private val mPaint = Paint()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mBitmap == null) {
            return
        }
        val bounds = RectF(0F, 0F, 400F, 400F)
        if (Build.VERSION.SDK_INT >= 21) canvas.saveLayer(bounds, mPaint) else canvas.saveLayer(
            bounds,
            mPaint,
            Canvas.ALL_SAVE_FLAG
        )
        canvas.drawColor(-0x10000)
        canvas.drawBitmap(mBitmap, 200F, 200F, mPaint)
        canvas.restoreToCount(1)
        canvas.drawBitmap(mBitmap, 400F, 400F, mPaint)
        invalidate()
    }

    init {
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_128x128)
        mPaint.style = Paint.Style.STROKE
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        mPaint.isAntiAlias = true // 抗锯尺
    }
}