package com.exsilicium.namehelper.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView.BufferType
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.exsilicium.namehelper.R
import com.exsilicium.namehelper.databinding.PersonDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonDetailFragment : Fragment() {
    private val args: PersonDetailFragmentArgs by navArgs()
    private var _binding: PersonDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.personId)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PersonDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.lookupPerson().collect { person ->
                if (person != null) {
                    (requireActivity() as AppCompatActivity).supportActionBar?.title =
                        person.getName()
                    binding.tvDateCreated.setText(
                        person.createdTime.toString(),
                        BufferType.NORMAL
                    )
                    binding.tvDateModified.setText(
                        person.lastModifiedTime.toString(),
                        BufferType.NORMAL
                    )
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.person_detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_delete -> deletePerson()
            R.id.btn_edit -> editPerson()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deletePerson(): Boolean {
        lifecycleScope.launch {
            viewModel.deletePerson()
        }.invokeOnCompletion {
            findNavController().popBackStack()
        }
        return true
    }

    private fun editPerson(): Boolean {
        findNavController().navigate(
            PersonDetailFragmentDirections.actionPersonDetailFragmentToPersonModifyFragment(
                args.personId
            )
        )
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
