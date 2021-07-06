package com.exsilicium.namehelper.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.exsilicium.namehelper.R

class PersonDetailFragment : Fragment() {

    private val args: PersonDetailFragmentArgs by navArgs()

    private lateinit var viewModel: PersonDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.person_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonDetailViewModel::class.java)
        // TODO: Use the ViewModel

        view.findViewById<TextView>(R.id.textview_name).text = args.id.toString()
    }

}
