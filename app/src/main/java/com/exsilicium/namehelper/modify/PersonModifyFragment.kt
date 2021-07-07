package com.exsilicium.namehelper.modify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.exsilicium.namehelper.databinding.PersonModifyFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonModifyFragment : Fragment() {
    private val args: PersonModifyFragmentArgs by navArgs()
    private var _binding: PersonModifyFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonModifyViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = PersonModifyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo how to show keyboard?

        viewModel.lookupPerson(args.personId).observe(viewLifecycleOwner, { person ->
            person?.let {
                binding.etFirstName.setText(it.firstName)
                binding.etLastName.setText(it.lastName)
            }
        })

        binding.btnSave.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.save(
                        binding.etFirstName.text.toString(),
                        binding.etLastName.text.toString(),
                )
            }.invokeOnCompletion {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
