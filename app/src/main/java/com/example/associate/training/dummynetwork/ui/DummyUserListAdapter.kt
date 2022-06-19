package com.example.associate.training.dummynetwork.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.databinding.ListDummyItemBinding
import com.example.associate.training.dummynetwork.data.DummyData

class DummyUserListAdapter :
    ListAdapter<DummyData, DummyUserListAdapter.DummyUserListViewHolder>(DiffCallback) {

    class DummyUserListViewHolder(
        var binding: ListDummyItemBinding
        ) : RecyclerView.ViewHolder(binding.root){

        fun bind(dummyData: DummyData) {
            binding.dummydata = dummyData
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DummyData>() {

        override fun areItemsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyUserListViewHolder {
        return DummyUserListViewHolder(
            ListDummyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DummyUserListViewHolder, position: Int) {
        val amphibian = getItem(position)
        holder.bind(amphibian)
    }
}

