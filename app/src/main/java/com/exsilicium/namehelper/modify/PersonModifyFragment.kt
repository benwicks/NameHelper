package com.exsilicium.namehelper.modify

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.getWindowInsetsController
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.exsilicium.namehelper.R
import com.exsilicium.namehelper.databinding.PersonModifyFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonModifyFragment : Fragment() {
    private val args: PersonModifyFragmentArgs by navArgs()
    private var _binding: PersonModifyFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonModifyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.personId)
    }

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
        viewModel.lookupPerson().observe(viewLifecycleOwner) { person ->
            if (person == null) {
                (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(R.string.add_person)
                binding.etFirstName.requestFocus()
                showKeyboard()
            } else {
                (requireActivity() as AppCompatActivity).supportActionBar?.title =
                    getString(R.string.edit_person_format, person.getName())
                binding.etFirstName.setText(person.firstName)
                binding.etLastName.setText(person.lastName)
            }
        }
        binding.btnSave.setOnClickListener { savePerson() }
    }

    private fun savePerson() {
        viewLifecycleOwner.lifecycleScope.launch { viewModel.save(this, binding) }
            .invokeOnCompletion { exception ->
                if (exception?.cause is InvalidInputException) {
                    Snackbar.make(
                        binding.root,
                        R.string.error_name_input_invalid,
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    hideKeyboard()
                    // TODO Replace to PersonDetailFragment screen (when adding) instead of popping to list?
                    findNavController().popBackStack()
                }
            }
    }

    private fun showKeyboard() {
//        getWindowInsetsController(binding.root)?.show(WindowInsetsCompat.Type.ime())
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    private fun hideKeyboard() {
        getWindowInsetsController(binding.root)?.hide(WindowInsetsCompat.Type.ime())
//        val inputMethodManager =
//            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
