package com.example.associate.training.canvas

import android.content.Context
import android.graphics.*
import android.view.View

/**
 * A View demo the Xfermode
 *
 * Give a checkerboard like to demo the different mode for two bitmaps(source & destination)
 *
 * @see <a href="https://developer.android.com/reference/android/graphics/PorterDuff.Mode">PorterDuff.Mode</a>
 */
class XfersModeView(context: Context?) : View(context) {

    private var srcBitmap: Bitmap
    private var dstBitmap: Bitmap
    // background checker-board pattern
    private val shader: Shader
    private val labelP = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paint = Paint()

    init {
        srcBitmap = makeSrc(64, 64)
        dstBitmap = makeDst(64, 64)
        shader = makeShader()
    }

    // Make a ckeckerboard pattern
    private fun makeShader(): BitmapShader {
        val bm = Bitmap.createBitmap(
            intArrayOf(
                0xFFFFFF, 0xBBBBBB,
                0xBBBBBB, 0xFFFFFF
            ), 2, 2,
            Bitmap.Config.RGB_565
        )
        val shader = BitmapShader(
            bm,
            Shader.TileMode.REPEAT,
            Shader.TileMode.REPEAT
        )
        val m = Matrix()
        m.setScale(15F, 15F)
        shader.setLocalMatrix(m)
        return shader
    }

    // create a bitmap with a circle, used for the "dst" image
    private fun makeDst(w: Int, h: Int): Bitmap {
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.rgb(215, 0, 77)
        c.drawOval(RectF(0F, 0F, (w * 3 / 4).toFloat(), (h * 3 / 4).toFloat()), p)
        return bm
    }

    // create a bitmap with a rect, used for the "src" image
    private fun makeSrc(w: Int, h: Int): Bitmap {
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.rgb(30, 129, 240)
        c.drawRect(
            (w / 3).toFloat(), (h / 3).toFloat(), (w * 19 / 20).toFloat(),
            (h * 19 / 20).toFloat(), p
        )
        return bm
    }
    private var widthItem = 0
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        // Calculate the radius from the smaller of the width and height.
        widthItem = width / ROW_MAX
        // Padding
        widthItem -= 40

        srcBitmap = makeSrc(widthItem, widthItem)
        dstBitmap = makeDst(widthItem, widthItem)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        labelP.textAlign = Paint.Align.CENTER
        labelP.textSize = 18F
        paint.isFilterBitmap = false
        canvas.translate(15F, 35F)
        var x = 0f
        var y = 0f
        val W = widthItem
        val H = widthItem
        var index = 0
        for (entry in map.entries) {
            // draw the border
            paint.style = Paint.Style.STROKE
            paint.shader = shader
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
            paint.xfermode = entry.key
            canvas.drawBitmap(srcBitmap, 0f, 0f, paint)
            paint.xfermode = null
            canvas.restoreToCount(sc)
            // draw the label
            canvas.drawText(
                entry.value,
                x + W / 2, y - labelP.textSize / 2, labelP
            )
            x += W + 40
            // wrap around when we've drawn enough for one row
            if (index % ROW_MAX == ROW_MAX - 1) {
                x = 0f
                y += H + 30
            }
            index += 1
        }
    }

    companion object {
        private const val ROW_MAX = 4 // number of samples per row
        private val map = mapOf(
            PorterDuffXfermode(PorterDuff.Mode.ADD) to "ADD",
            PorterDuffXfermode(PorterDuff.Mode.CLEAR) to "Clear",
            PorterDuffXfermode(PorterDuff.Mode.DARKEN) to "DARKEN",
            PorterDuffXfermode(PorterDuff.Mode.DST) to "DST",
            PorterDuffXfermode(PorterDuff.Mode.DST_ATOP) to "DST_ATOP",
            PorterDuffXfermode(PorterDuff.Mode.DST_IN) to "DST_IN",
            PorterDuffXfermode(PorterDuff.Mode.DST_OUT) to "DST_OUT",
            PorterDuffXfermode(PorterDuff.Mode.DST_OVER) to "DST_OVER",
            PorterDuffXfermode(PorterDuff.Mode.LIGHTEN) to "LIGHTEN",
            PorterDuffXfermode(PorterDuff.Mode.MULTIPLY) to "MULTIPLY",
            PorterDuffXfermode(PorterDuff.Mode.OVERLAY) to "OVERLAY",
            PorterDuffXfermode(PorterDuff.Mode.SCREEN) to "SCREEN",
            PorterDuffXfermode(PorterDuff.Mode.SRC) to "SRC",
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP) to "SRC_ATOP",
            PorterDuffXfermode(PorterDuff.Mode.SRC_IN) to "SRC_IN",
            PorterDuffXfermode(PorterDuff.Mode.SRC_OUT) to "SRC_OUT",
            PorterDuffXfermode(PorterDuff.Mode.SRC_OVER) to "SRC_OVER",
            PorterDuffXfermode(PorterDuff.Mode.XOR) to "XOR")
    }

}