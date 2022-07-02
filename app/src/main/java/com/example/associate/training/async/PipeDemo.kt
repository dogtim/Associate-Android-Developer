package com.example.associate.training.async

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import java.io.IOException
import java.io.PipedReader
import java.io.PipedWriter

class PipeDemo {
    private var r: PipedReader = PipedReader()
    private var w: PipedWriter = PipedWriter()
    private var workerThread: Thread? = null

    fun onViewCreated(threadEdit: AppCompatEditText) {

        try {
            w.connect(r)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        threadEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                try {
                    // Only handle addition of characters
                    Log.d("Dogtim", "charSequence = $charSequence")
                    if (count > before) {
                        w.write(charSequence.subSequence(before, count).toString())
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })


        workerThread = Thread(TextHandlerTask(r))
        workerThread!!.start()
    }

    fun onDestroyView() {
        workerThread!!.interrupt()
        try {
            r.close()
            w.close()
        } catch (e: IOException) {
        }
    }
}
private class TextHandlerTask(private val reader: PipedReader) : Runnable {
    override fun run() {
        Log.d("Dogtim", "Pre thread")
        //while (Thread.currentThread().isInterrupted) {
        Log.d("Dogtim", "while loop thread")
        try {
            var i: Int
            while (reader.read().also { i = it } != -1) {
                val c = i.toChar()
                //ADD TEXT PROCESSING LOGIC HERE
                Log.d("Dogtim", "char = $c")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //}
    }
}