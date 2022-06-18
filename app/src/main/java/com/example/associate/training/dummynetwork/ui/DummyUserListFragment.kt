package com.example.associate.training.dummynetwork.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.associate.training.R
import com.example.associate.training.amphibian.ui.AmphibianListAdapter
import com.example.associate.training.amphibian.ui.AmphibianListener
import com.example.associate.training.amphibian.ui.AmphibianViewModel
import com.example.associate.training.databinding.FragmentAmphibianListBinding
import com.example.associate.training.databinding.FragmentDummyUserListBinding

class DummyUserListFragment : Fragment() {

    private val viewModel: DummyUserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDummyUserListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = DummyUserListAdapter()

        // Inflate the layout for this fragment
        return binding.root
    }
}
