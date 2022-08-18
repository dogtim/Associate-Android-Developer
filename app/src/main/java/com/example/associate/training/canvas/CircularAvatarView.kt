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
        val bitmap = cropCircle(avatarBitmap)
        canvas.drawBitmap(bitmap, 200F, 200F, paint)
    }

    private fun cropCircle(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle(
            (bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat(),
            (bitmap.width / 2).toFloat(), paint
        )
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    init {
        paint.isAntiAlias = true
    }
}