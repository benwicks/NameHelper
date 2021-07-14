package com.exsilicium.namehelper.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.exsilicium.namehelper.data.Person
import com.exsilicium.namehelper.databinding.PersonListContentBinding

class PersonRecyclerViewAdapter :
    PagingDataAdapter<Person, PersonRecyclerViewAdapter.ViewHolder>(PersonDiffCallback()) {

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
        holder.bindTo(getItem(position))
    }

    inner class ViewHolder(binding: PersonListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvId: TextView = binding.id
        private val tvName: TextView = binding.content
        var person: Person? = null

        override fun toString(): String {
            return super.toString() + " '${tvName.text}'"
        }

        fun bindTo(person: Person?) {
            this.person = person
            val personId = person?.id
            tvId.text = personId.toString()
            tvName.text = person?.name

            itemView.setOnClickListener {
                personId?.let { personId ->
                    it.findNavController().navigate(
                        PersonListFragmentDirections.actionPersonListFragmentToPersonDetailFragment(
                            personId
                        )
                    )
                }
            }
        }
    }

    class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
    }
}
