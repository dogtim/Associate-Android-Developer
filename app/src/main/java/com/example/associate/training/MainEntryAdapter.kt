package com.example.associate.training

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.busschedule.BusScheduleActivity
import com.example.associate.training.workmanager.BlurWorkManagerActivity

data class Entry(val name: String, val classk: Class<*>?)

class MainEntryAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val entries: List<Entry> = listOf(
        Entry("WorkManager", BlurWorkManagerActivity::class.java),
        Entry("ROOM Database with sql", BusScheduleActivity::class.java))
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_timeline, parent, false)
        return DesignViewHolder(view)
    }

    override fun onBindViewHolder(@Nullable holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DesignViewHolder) {
            val entry = entries[position]
            holder.textView.text = entry.name
            val context = holder.itemView.context

            holder.itemView.setOnClickListener {
                val intent = Intent(context, entry.classk)
                context.startActivity(intent)
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

class DesignViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    val textView: TextView = itemView.findViewById(R.id.textView)
}