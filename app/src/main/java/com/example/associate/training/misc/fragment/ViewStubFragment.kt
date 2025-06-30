package com.example.associate.training.misc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.associate.training.R
import com.example.associate.training.canvas.CircularAvatarView
import com.example.associate.training.canvas.SaveLayerView
import com.example.associate.training.canvas.SaveRestoreCanvasView
import com.example.associate.training.canvas.XfersModeView
import com.example.associate.training.canvas.view.CanvasViewType
import com.example.associate.training.databinding.ActivityMainBinding
import com.example.associate.training.databinding.FragmentViewStubBinding

class ViewStubFragment : Fragment() {
    private lateinit var binding: FragmentViewStubBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewStubBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            helloWorld.setOnClickListener {
                viewStub.layoutResource = R.layout.hello_world
                viewStub.inflate()
            }

            hellWorld.setOnClickListener {
                viewStub.layoutResource = R.layout.hell_world
                viewStub.inflate()
            }
        }
    }
}