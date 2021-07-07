package com.exsilicium.namehelper.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.exsilicium.namehelper.databinding.PersonDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailFragment : Fragment() {
    private val args: PersonDetailFragmentArgs by navArgs()
    private var _binding: PersonDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonDetailViewModel by viewModels()

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
        viewModel.lookupPerson(args.personId).observe(viewLifecycleOwner) { person ->
            binding.tvName.text = person.getName()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
