
package com.example.associate.training.async

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.associate.training.databinding.FragmentThreadBinding

// This codes come from below website
// https://www.oreilly.com/library/view/efficient-android-threading/9781449364120/ch04.html
class ThreadFunFragment: Fragment() {

    private var _binding: FragmentThreadBinding? = null
    private val binding get() = _binding!!
    private val pipeDemo = PipeDemo()
    private val looperDemo = LooperDemo()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThreadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pipeDemo.onViewCreated(binding.threadEdit)
        looperDemo.onCreate()
        binding.looperButton.setOnClickListener {
            looperDemo.onClick()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pipeDemo.onDestroyView()
        looperDemo.onDestroy()
        _binding = null
    }

}
