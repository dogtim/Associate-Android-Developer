package com.example.associate.training.dummy.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.associate.training.R
import com.example.associate.training.databinding.ListDummyItemBinding
import com.example.associate.training.dummy.data.DummyData

class DummyUserListAdapter :
    ListAdapter<DummyData, DummyUserListAdapter.DummyUserListViewHolder>(DiffCallback) {

    class DummyUserListViewHolder(
        var binding: ListDummyItemBinding
        ) : RecyclerView.ViewHolder(binding.root){

        fun bind(dummyData: DummyData) {
            binding.dummydata = dummyData
            dummyData.picture.let {
                binding.dummyDataUserAvatar.load(it) {
                    crossfade(true)
                    placeholder(R.drawable.ic_disconnect_placeholder)
                    transformations(CircleCropTransformation())
                }
            }
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

