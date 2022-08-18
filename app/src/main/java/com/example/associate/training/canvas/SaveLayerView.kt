package com.example.associate.training.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

// https://www.cnblogs.com/liangstudyhome/p/4143498.html
class SaveLayerView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    AppCompatImageView(context!!, attrs) {
    private val paint = Paint()
    private val rect = RectF(0F, 0F, 200F, 200F)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.translate(10F, 10F)
        paint.color = Color.RED
        canvas.drawCircle(75F, 75F, 75F, paint)
        canvas.saveLayerAlpha(rect, 0x88)
        paint.color = Color.BLUE
        canvas.drawCircle(125F, 125F, 75F, paint)
        canvas.restore()
    }

    init {
        paint.isAntiAlias = true
    }
}