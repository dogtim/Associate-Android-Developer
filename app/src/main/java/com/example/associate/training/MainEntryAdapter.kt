package com.example.associate.training

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.workmanager.workers.BlurWorkManagerActivity

class MainEntryAdapter(private val timeLines: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_timeline, parent, false)
        return DesignViewHolder(view)
    }

    override fun onBindViewHolder(@Nullable holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DesignViewHolder) {
            val tutorName = timeLines[position]
            holder.textView.text = tutorName
            val context = holder.itemView.context

            holder.itemView.setOnClickListener {
                val intent = Intent(context, BlurWorkManagerActivity::class.java)
                context.startActivity(intent)
            }
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return timeLines.size
    }

    class DesignViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}

class DesignViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
    val textView: TextView = itemView.findViewById(R.id.textView)
}
