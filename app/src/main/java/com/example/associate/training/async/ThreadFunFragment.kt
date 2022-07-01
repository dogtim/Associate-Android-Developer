
package com.example.associate.training.async

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.associate.training.databinding.ThreadFragmentBinding
import java.io.IOException
import java.io.PipedReader
import java.io.PipedWriter


// https://www.oreilly.com/library/view/efficient-android-threading/9781449364120/ch04.html
class ThreadFunFragment: Fragment() {

    private var _binding: ThreadFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var r: PipedReader
    private lateinit var w: PipedWriter
    private var workerThread: Thread? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ThreadFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        r = PipedReader()
        w = PipedWriter()

        try {
            w.connect(r)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        binding.threadEdit.addTextChangedListener(object : TextWatcher {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        workerThread!!.interrupt()
        try {
            r.close()
            w.close()
        } catch (e: IOException) {
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
}
