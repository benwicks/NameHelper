package com.exsilicium.namehelper.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.exsilicium.namehelper.data.Person
import com.exsilicium.namehelper.databinding.PersonListContentBinding
import com.exsilicium.namehelper.databinding.PersonListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonListFragment : Fragment() {
    private val viewModel: PersonListViewModel by viewModels()
    private var _binding: PersonListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = PersonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(PersonListFragmentDirections.actionPersonListFragmentToPersonModifyFragment())
        }

        setupRecyclerView(binding.personList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        viewModel.getAllPeople().observe(viewLifecycleOwner, {
            recyclerView.adapter = SimpleItemRecyclerViewAdapter(it)
        })
    }

    inner class SimpleItemRecyclerViewAdapter(
            private val values: List<Person>
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(PersonListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mItem = values[position]
            holder.mIdView.text = values[position].id.toString()
            holder.mContentView.text = values[position].getName()

            holder.itemView.setOnClickListener { v ->
                v.findNavController().navigate(PersonListFragmentDirections.actionPersonListFragmentToPersonDetailFragment(holder.mItem!!.id))
            }
        }

        override fun getItemCount(): Int {
            return values.size
        }

        inner class ViewHolder(binding: PersonListContentBinding) : RecyclerView.ViewHolder(binding.root) {
            val mIdView: TextView = binding.id
            val mContentView: TextView = binding.content
            var mItem: Person? = null

            override fun toString(): String {
                return super.toString() + " '${mContentView.text}'"
            }
        }
    }
}
