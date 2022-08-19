package com.example.associate.training.canvas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.associate.training.canvas.CircularAvatarView
import com.example.associate.training.canvas.SaveLayerView
import com.example.associate.training.canvas.SaveRestoreCanvasView
import com.example.associate.training.canvas.XfersModeView

class CanvasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return arguments?.let {
            when(it.getInt("selectId")) {
                0 -> XfersModeView(context)
                1 -> CircularAvatarView(context)
                2 -> SaveLayerView(context)
                3 -> SaveRestoreCanvasView(context)
                else -> {
                    XfersModeView(context)
                }
            }
        }
    }

}