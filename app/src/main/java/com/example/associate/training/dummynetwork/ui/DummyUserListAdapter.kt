/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.associate.training.dummynetwork.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.associate.training.amphibian.ui.AmphibianListener
import com.example.associate.training.databinding.ListDummyItemBinding
import com.example.associate.training.dummynetwork.data.DummyData

class DummyUserListAdapter() :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyUserListAdapter.DummyUserListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return DummyUserListAdapter.DummyUserListViewHolder(
            ListDummyItemBinding.inflate(layoutInflater, parent, false)

        )
    }

    override fun onBindViewHolder(holder: DummyUserListAdapter.DummyUserListViewHolder, position: Int) {
        val amphibian = getItem(position)
        holder.bind(amphibian)
    }
}

