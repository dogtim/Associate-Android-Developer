package com.example.associate.training.views.layout
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.R

class TextItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView: TextView
    fun bind(text: String?) {
        textView.setText(text)
    }

    init {
        textView = itemView.findViewById<View>(R.id.list_item) as TextView
    }
}