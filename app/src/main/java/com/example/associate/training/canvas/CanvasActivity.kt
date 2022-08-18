package com.example.associate.training.canvas

import android.content.Intent
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.Entry
import com.example.associate.training.MainEntryAdapter
import com.example.associate.training.R
import com.example.associate.training.amphibian.AmphibianActivity
import com.example.associate.training.async.ThreadActivity
import com.example.associate.training.busschedule.BusScheduleActivity
import com.example.associate.training.databinding.ActivityMainBinding
import com.example.associate.training.databinding.FragmentWordListBinding
import com.example.associate.training.dummy.DummyActivity
import com.example.associate.training.inventory.InventoryActivity
import com.example.associate.training.lifecycle.LifecycleActivity
import com.example.associate.training.pic.PicActivity
import com.example.associate.training.word.WordActivity
import com.example.associate.training.word.WordAdapter
import com.example.associate.training.word.WordListFragment
import com.example.associate.training.workmanager.WorkManagerActivity

// Please refer to https://developer.android.com/reference/android/graphics/PorterDuff.Mode
class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFragment()
        // setContentView(XfersModeView(this))
        // setContentView(CircularAvatarView(this))
        // setContentView(SaveLayerView(this))
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

    fun getList(): List<Entry> {
        return listOf(
            Entry("ViewsActivity", PicActivity::class.java),
            Entry("WorkManager", WorkManagerActivity::class.java),
            Entry("ROOM Database \n sql", BusScheduleActivity::class.java),
            Entry("Amphibian \n Retrofit, Moshi, DataBinding", AmphibianActivity::class.java),
            Entry("WordActivity", WordActivity::class.java),
            Entry("DummyActivity", DummyActivity::class.java),
            Entry("InventoryActivity", InventoryActivity::class.java),
            Entry("LifecycleActivity", LifecycleActivity::class.java),
            Entry("ThreadActivity", ThreadActivity::class.java),
            Entry("CanvasActivity", CanvasActivity::class.java)
        )
    }

}

class TestAdapter(private val entries: List<Entry>, private val activity: AppCompatActivity) :
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

                activity.supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<WordListTestFragment>(R.id.canvas_fragment_container)
                }
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
    ): View {
        // Retrieve and inflate the layout for this fragment
        return XfersModeView(context)
    }

}
