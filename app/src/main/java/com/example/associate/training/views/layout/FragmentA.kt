package com.example.associate.training.views.layout

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.R

class FragmentA : Fragment() {
    var recyclerView: RecyclerView? = null
    var list: ListView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = resources.getStringArray(R.array.tab_A)
        val adapter = RecyclerViewAdapter(items)
        recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(
            context
        )
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = adapter
    }
}