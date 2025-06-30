package com.example.associate.training.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.associate.training.R
import com.example.associate.training.canvas.CircularAvatarView
import com.example.associate.training.canvas.SaveLayerView
import com.example.associate.training.canvas.SaveRestoreCanvasView
import com.example.associate.training.canvas.XfersModeView
import com.example.associate.training.canvas.view.CanvasViewType
import com.example.associate.training.databinding.ActivityMainBinding
import com.example.associate.training.databinding.FragmentViewsBinding
import com.example.associate.training.inventory.ItemListAdapter
import com.example.associate.training.inventory.ItemListFragmentDirections
import com.example.associate.training.views.layout.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class TabLayoutFragment : Fragment() {
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: FragmentViewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.viewPager

        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        binding.tabs.setupWithViewPager(viewPager)
    }
}