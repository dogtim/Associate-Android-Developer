
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


// This codes come from below website
// https://www.oreilly.com/library/view/efficient-android-threading/9781449364120/ch04.html
class ThreadFunFragment: Fragment() {

    private var _binding: ThreadFragmentBinding? = null
    private val binding get() = _binding!!
    private val pipeDemo = PipeDemo()

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
        pipeDemo.onViewCreated(binding.threadEdit)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        pipeDemo.onDestroyView()
        _binding = null
    }

}
