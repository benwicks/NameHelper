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
        holder.person = currentList[position]
        holder.tvId.text = currentList[position].id.toString()
        holder.tvName.text = currentList[position].getName()

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(
                PersonListFragmentDirections.actionPersonListFragmentToPersonDetailFragment(
                    holder.person.id
                )
            )
        }
    }

    inner class ViewHolder(binding: PersonListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvId: TextView = binding.id
        val tvName: TextView = binding.content
        lateinit var person: Person

        override fun toString(): String {
            return super.toString() + " '${tvName.text}'"
        }
    }

    class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
    }
}
