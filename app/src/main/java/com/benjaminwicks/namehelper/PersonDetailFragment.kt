package com.benjaminwicks.namehelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.benjaminwicks.namehelper.dummy.DummyContent
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * A fragment representing a single Person detail screen.
 * This fragment is either contained in a [PersonListActivity]
 * in two-pane mode (on tablets) or a [PersonDetailActivity]
 * on handsets.
 */
class PersonDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: DummyContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.containsKey(ARG_ITEM_ID) == true) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP[arguments?.getString(ARG_ITEM_ID)]

            val appBarLayout = activity?.findViewById<CollapsingToolbarLayout?>(R.id.toolbar_layout)
            appBarLayout?.title = mItem!!.content
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.person_detail, container, false)
        mItem?.let {
            // Show the dummy content as text in a TextView.
            (rootView.findViewById<TextView>(R.id.person_detail)).text = it.details
        }
        return rootView
    }

    companion object {

        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
