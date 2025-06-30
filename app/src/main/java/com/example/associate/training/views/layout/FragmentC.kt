package com.example.associate.training.views.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.R

class FragmentC : Fragment() {
    var recyclerView: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items: Array<String> = getResources().getStringArray(R.array.tab_C)
        val adapter = RecyclerViewAdapter(items)
        recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(getContext())
        recyclerView!!.setLayoutManager(layoutManager)
        recyclerView!!.setAdapter(adapter)
    }
}