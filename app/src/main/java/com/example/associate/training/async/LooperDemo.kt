package com.example.associate.training.async

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class LooperDemo {

    lateinit var mLooperThread: LooperThread

    fun onCreate() {
        mLooperThread =
            LooperThread() // Start the worker thread, so that it is ready to process messages.
        mLooperThread.start();
    }

    fun onClick() {
        // There is race condition between the setup of mHandler on a background thread and this usage on the UI thread. Hence, validate that mHandler is available.
        if (mLooperThread.mHandler != null) {

            //Initialize a Message-object with the what argument arbitrarily set to 0.
            val msg = mLooperThread.mHandler.obtainMessage(0)
            mLooperThread.mHandler.sendMessage(msg)
        }
    }

    fun onDestroy() {
        mLooperThread.mHandler.getLooper().quit()
        // Terminate the background thread. The call to Looper.quit() stops the dispatching of messages and releases Looper.loop()
        // from blocking so the run method can finish, leading to the termination of the thread.
    }
}

class LooperThread : Thread() {

    lateinit var mHandler: Handler

    fun doLongRunningOperation() {
        Log.i("LooperThread", "doLongRunningOperation")
    }
    override fun run() {
        Looper.prepare() // Associate a Looper—and implicitly a MessageQueue—with the thread.
        mHandler = object : Handler(Looper.myLooper()!!) {
            // Set up a Handler to be used by the producer for inserting messages in the queue.
            // Here we use the default constructor so it will bind to the Looper of the current thread.
            // Hence, this Handler can created only after Looper.prepare(), or it will have nothing to bind to.
            override fun handleMessage(msg: Message) {
                // Callback that runs when the message has been dispatched to the worker thread.
                // It checks the what parameter and then executes the long operation.
                if (msg.what == 0) {
                    doLongRunningOperation();
                }
            }
        }

        Looper.loop()
        // Start dispatching messages from the message queue to the consumer thread. This is a blocking call, so the worker thread will not finish.
    }
}