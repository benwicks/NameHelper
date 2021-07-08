package com.exsilicium.namehelper.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.exsilicium.namehelper.R
import com.exsilicium.namehelper.databinding.PersonListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonListFragment : Fragment() {
    private val viewModel: PersonListViewModel by viewModels()
    private var _binding: PersonListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().lifecycle.coroutineScope.launchWhenCreated {
            viewModel.getAllPeople()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(R.string.app_name)
        _binding = PersonListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            findNavController().navigate(PersonListFragmentDirections.actionPersonListFragmentToPersonModifyFragment())
        }
        binding.personList.adapter = viewModel.adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
