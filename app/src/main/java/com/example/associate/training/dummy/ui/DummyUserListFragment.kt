package com.example.associate.training.dummy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.associate.training.databinding.FragmentDummyUserListBinding

class DummyUserListFragment : Fragment() {

    private val viewModel: DummyUserViewModel by activityViewModels()
    private lateinit var binding: FragmentDummyUserListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDummyUserListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = DummyUserListAdapter()

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter as DummyUserListAdapter

        viewModel.dummyUserList.observe(this.viewLifecycleOwner) { selectedItem ->
            val adapter = binding.recyclerView.adapter as DummyUserListAdapter
            adapter.submitList(selectedItem)
        }
    }
}
