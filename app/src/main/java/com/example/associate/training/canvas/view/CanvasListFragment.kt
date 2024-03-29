package com.example.associate.training.canvas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.R
import com.example.associate.training.databinding.ActivityMainBinding

class CanvasListFragment : Fragment() {

    private lateinit var adapter: CanvasListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setupAdapter() {
        adapter = CanvasListAdapter(CanvasViewType.values().toList(), activity as AppCompatActivity)
        binding.tutorsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.tutorsRecyclerView.adapter = adapter
    }

}

enum class CanvasViewType { XFERS_MODE, CIRCULAR_AVATAR, SAVE_LAYER, SAVE_RESTORE }

class CanvasListAdapter(private val entries: List<CanvasViewType>, private val activity: AppCompatActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_timeline, parent, false)
        return DesignViewHolder(view)
    }

    override fun onBindViewHolder(@Nullable holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DesignViewHolder) {
            val entry = entries[position]
            holder.textView.text = entry.name
            holder.itemView.setOnClickListener {
                val bundle = bundleOf(CanvasFragment.argumentOfViewType to entry.ordinal)
                activity.supportFragmentManager.commit {
                    replace<CanvasFragment>(R.id.canvas_fragment_container, args = bundle)
                    addToBackStack(null)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return entries.size
    }

    class DesignViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}