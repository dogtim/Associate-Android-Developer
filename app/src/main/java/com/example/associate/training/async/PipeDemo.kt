package com.example.associate.training.async

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import java.io.IOException
import java.io.PipedReader
import java.io.PipedWriter

class PipeDemo {
    private var reader: PipedReader = PipedReader()
    private var writer: PipedWriter = PipedWriter()
    private var workerThread: Thread? = null

    fun onViewCreated(threadEdit: AppCompatEditText) {

        try {
            writer.connect(reader)
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
                        writer.write(charSequence.subSequence(before, count).toString())
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        workerThread = Thread(TextHandlerTask(reader))
        workerThread!!.start()
    }

    fun onDestroyView() {
        workerThread!!.interrupt()
        try {
            reader.close()
            writer.close()
        } catch (e: IOException) {
        }
    }
}

private class TextHandlerTask(private val reader: PipedReader) : Runnable {
    override fun run() {
        try {
            var i: Int
            while (reader.read().also { i = it } != -1) {
                val c = i.toChar()
                //ADD TEXT PROCESSING LOGIC HERE
                Log.d(this::class.simpleName, "char = $c")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}