package com.example.associate.training.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.associate.training.R

// Try to draw a photo in a circular shape
class CircularAvatarView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    AppCompatImageView(context!!, attrs) {
    private val paint = Paint()
    private val avatarBitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar_128x128)

    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)

    private val circularBitmap: Bitmap by lazy {
        val w = avatarBitmap.width
        val h = avatarBitmap.height
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.isAntiAlias = true
        p.color = Color.rgb(215, 0, 77)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 10F
        c.drawOval(RectF(0F, 0F, w.toFloat(), h.toFloat()), p)

        bm
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(avatarBitmap, 200F, 200F, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(circularBitmap, 200F, 200F, paint)

    }

    init {
        paint.isAntiAlias = true
    }
}