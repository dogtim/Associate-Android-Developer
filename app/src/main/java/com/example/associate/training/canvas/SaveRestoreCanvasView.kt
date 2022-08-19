package com.example.associate.training.canvas

import com.example.associate.training.R
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

// https://www.twle.cn/l/yufei/android/android-basic-canvas-savelayer.html
class SaveRestoreCanvasView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    AppCompatImageView(context!!, attrs) {
    private val bounds = RectF(0F, 0F, 400F, 400F)
    private val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_128x128)
    private val paint = Paint()

    init {
        paint.style = Paint.Style.STROKE
        paint.flags = Paint.ANTI_ALIAS_FLAG
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.saveLayer(bounds, paint)
        canvas.drawColor(-0x10000)
        canvas.drawBitmap(bitmap, 200F, 200F, paint)
        canvas.restoreToCount(1)
        canvas.drawBitmap(bitmap, 400F, 400F, paint)
    }

}