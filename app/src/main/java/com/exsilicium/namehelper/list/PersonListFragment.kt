package com.exsilicium.namehelper.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.exsilicium.namehelper.R
import com.exsilicium.namehelper.dummy.DummyContent
import com.google.android.material.snackbar.Snackbar

class PersonListFragment : Fragment() {
    private lateinit var viewModel: PersonListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.person_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)

        view.findViewById<View>(R.id.fab).setOnClickListener { fab ->
            Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        setupRecyclerView(view.findViewById(R.id.person_list))
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        // TODO: Use the ViewModel?
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(DummyContent.ITEMS)
    }

    inner class SimpleItemRecyclerViewAdapter(
            private val values: List<DummyContent.DummyItem>
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.person_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mItem = values[position]
            holder.mIdView.text = values[position].id.toString()
            holder.mContentView.text = values[position].content

            holder.mView.setOnClickListener { v ->
                v.findNavController().navigate(PersonListFragmentDirections.actionPersonListFragmentToPersonDetailFragment(holder.mItem!!.id))
            }
        }

        override fun getItemCount(): Int {
            return values.size
        }

        inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView = mView.findViewById(R.id.id)
            val mContentView: TextView = mView.findViewById(R.id.content)
            var mItem: DummyContent.DummyItem? = null

            override fun toString(): String {
                return super.toString() + " '${mContentView.text}'"
            }
        }
    }
}
