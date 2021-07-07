package com.exsilicium.namehelper.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exsilicium.namehelper.data.Person
import com.exsilicium.namehelper.databinding.PersonListContentBinding

// todo implement DiffUtil when adding/editing/deleting list items (https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding#3)
class PersonRecyclerViewAdapter :
    ListAdapter<Person, PersonRecyclerViewAdapter.ViewHolder>(PersonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PersonListContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = currentList[position]
        holder.mIdView.text = currentList[position].id.toString()
        holder.mContentView.text = currentList[position].getName()

        holder.itemView.setOnClickListener { v ->
            v.findNavController().navigate(
                PersonListFragmentDirections.actionPersonListFragmentToPersonDetailFragment(
                    holder.mItem!!.id
                )
            )
        }
    }

    inner class ViewHolder(binding: PersonListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mIdView: TextView = binding.id
        val mContentView: TextView = binding.content
        var mItem: Person? = null

        override fun toString(): String {
            return super.toString() + " '${mContentView.text}'"
        }
    }

    class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
    }
}
