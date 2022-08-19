package com.example.associate.training.canvas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.R
import com.example.associate.training.databinding.ActivityMainBinding

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFragment()
    }

    private fun createFragment() {
        setContentView(R.layout.activity_canvas)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<CanvasListFragment>(R.id.canvas_fragment_container)
        }
    }

}

class CanvasListFragment : Fragment() {

    private lateinit var adapter: TestAdapter
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
        adapter = TestAdapter(getList(), activity as AppCompatActivity)
        binding.tutorsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        binding.tutorsRecyclerView.adapter = adapter
    }

    fun getList(): List<TestEntry> {
        return listOf(
            TestEntry("XfersModeView"),
            TestEntry("CircularAvatarView"),
            TestEntry("SaveLayerView"),
            TestEntry("SaveRestoreCanvasView")
        )

    }

}

data class TestEntry(val name: String)

class TestAdapter(private val entries: List<TestEntry>, private val activity: AppCompatActivity) :
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

                val fragment = WordListTestFragment()
                val bundle = Bundle()
                bundle.putInt("selectId", position)
                fragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction().replace(R.id.canvas_fragment_container, fragment).addToBackStack(null).commit()
            }
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return entries.size
    }

    class DesignViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}

class WordListTestFragment : Fragment() {

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
