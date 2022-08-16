package com.example.associate.training

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

// Please refer to https://developer.android.com/reference/android/graphics/PorterDuff.Mode
class XfermodesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(SampleView(this))
    }

    private class SampleView(context: Context?) : View(context) {
        private val srcBitmap: Bitmap
        private val dstBitmap: Bitmap
        // background checker-board pattern
        private val shader: Shader
        private val labelP = Paint(Paint.ANTI_ALIAS_FLAG)
        private val paint = Paint()

        // create a bitmap with a circle, used for the "dst" image
        fun makeDst(w: Int, h: Int): Bitmap {
            val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val c = Canvas(bm)
            val p = Paint(Paint.ANTI_ALIAS_FLAG)
            p.color = -0x33bc
            c.drawOval(RectF(0F, 0F, (w * 3 / 4).toFloat(), (h * 3 / 4).toFloat()), p)
            return bm
        }

        // create a bitmap with a rect, used for the "src" image
        fun makeSrc(w: Int, h: Int): Bitmap {
            val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val c = Canvas(bm)
            val p = Paint(Paint.ANTI_ALIAS_FLAG)
            p.color = -0x995501
            c.drawRect(
                (w / 3).toFloat(), (h / 3).toFloat(), (w * 19 / 20).toFloat(),
                (h * 19 / 20).toFloat(), p
            )
            return bm
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawColor(Color.WHITE)
            labelP.textAlign = Paint.Align.CENTER
            paint.isFilterBitmap = false
            canvas.translate(15F, 35F)
            var x = 0f
            var y = 0f
            for (i in sModes.indices) {
                // draw the border
                paint.style = Paint.Style.STROKE
                paint.shader = null
                canvas.drawRect(
                    x - 0.5f, y - 0.5f,
                    x + W + 0.5f, y + H + 0.5f, paint
                )
                // draw the checker-board pattern
                paint.style = Paint.Style.FILL
                paint.shader = shader
                canvas.drawRect(x, y, x + W, y + H, paint)
                // draw the src/dst example into our offscreen bitmap
                val sc: Int = canvas.saveLayer(x, y, x + W, y + H, null)
                canvas.translate(x, y)
                canvas.drawBitmap(dstBitmap, 0f, 0f, paint)
                paint.xfermode = sModes[i]
                canvas.drawBitmap(srcBitmap, 0f, 0f, paint)
                paint.xfermode = null
                canvas.restoreToCount(sc)
                // draw the label
                canvas.drawText(
                    sLabels[i],
                    x + W / 2, y - labelP.textSize / 2, labelP
                )
                x += W + 10
                // wrap around when we've drawn enough for one row
                if (i % ROW_MAX == ROW_MAX - 1) {
                    x = 0f
                    y += H + 30
                }
            }
        }

        companion object {
            private const val W = 64
            private const val H = 64
            private const val ROW_MAX = 4 // number of samples per row
            private val sModes = arrayOf<Xfermode>(
                PorterDuffXfermode(PorterDuff.Mode.CLEAR),
                PorterDuffXfermode(PorterDuff.Mode.SRC),
                PorterDuffXfermode(PorterDuff.Mode.DST),
                PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
                PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
                PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
                PorterDuffXfermode(PorterDuff.Mode.DST_IN),
                PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
                PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
                PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
                PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
                PorterDuffXfermode(PorterDuff.Mode.XOR),
                PorterDuffXfermode(PorterDuff.Mode.DARKEN),
                PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
                PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
                PorterDuffXfermode(PorterDuff.Mode.SCREEN)
            )

            private val sLabels = arrayOf(
                "Clear", "Src", "Dst", "SrcOver",
                "DstOver", "SrcIn", "DstIn", "SrcOut",
                "DstOut", "SrcATop", "DstATop", "Xor",
                "Darken", "Lighten", "Multiply", "Screen"
            )
        }

        init {
            srcBitmap = makeSrc(W, H)
            dstBitmap = makeDst(W, H)
            // make a ckeckerboard pattern
            val bm = Bitmap.createBitmap(
                intArrayOf(
                    -0x1, -0x333334,
                    -0x333334, -0x1
                ), 2, 2,
                Bitmap.Config.RGB_565
            )
            shader = BitmapShader(
                bm,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
            )
            val m = Matrix()
            m.setScale(6F, 6F)
            shader.setLocalMatrix(m)
        }
    }
}