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

    companion object {
        const val argumentOfViewType = "selectId"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return arguments?.let {
            val type = it.getInt(argumentOfViewType)
            return when(CanvasViewType.values()[type]) {
                CanvasViewType.XFERS_MODE -> XfersModeView(context)
                CanvasViewType.CIRCULAR_AVATAR -> CircularAvatarView(context)
                CanvasViewType.SAVE_LAYER -> SaveLayerView(context)
                CanvasViewType.SAVE_RESTORE -> SaveRestoreCanvasView(context)
            }
        }
    }
}