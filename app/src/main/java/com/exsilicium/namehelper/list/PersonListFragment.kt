package com.exsilicium.namehelper.list

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.exsilicium.namehelper.R
import com.exsilicium.namehelper.databinding.PersonListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonListFragment : Fragment() {
    private val viewModel: PersonListViewModel by viewModels()
    private var _binding: PersonListFragmentBinding? = null
    private val binding get() = _binding!!

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
        setUpItemTouchHelper()
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.isEmpty().collect { binding.tvEmptyListGetStarted.isVisible = it }
        }
    }

    private fun setUpItemTouchHelper() {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                // we want to cache these and not allocate anything repeatedly in the onChildDraw method
                var background: Drawable? = null
                var xMark: Drawable? = null
                var xMarkMargin = 0
                var initiated = false
                private fun init() {
                    background = ColorDrawable(Color.RED)
                    xMark = ContextCompat.getDrawable(requireContext(), R.drawable.ic_clear_24dp)
                    xMark!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                    xMarkMargin = resources.getDimension(R.dimen.ic_clear_margin).toInt()
                    initiated = true
                }

                // not important, we don't want drag & drop
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                    val position = viewHolder.bindingAdapterPosition
                    val adapter = recyclerView.adapter as PersonRecyclerViewAdapter
                    return if (adapter.isUndoOn() && adapter.isPendingRemoval(position)) {
                        0
                    } else super.getSwipeDirs(recyclerView, viewHolder)
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//                    val swipedPosition = viewHolder.adapterPosition
//                    val adapter = binding.personList.adapter as PersonRecyclerViewAdapter
//                    val undoOn: Boolean = adapter.isUndoOn()
//                    if (undoOn) {
//                        adapter.pendingRemoval(swipedPosition)
//                    } else {
                    viewLifecycleOwner.lifecycle.coroutineScope.launch {
                        viewModel.remove(viewHolder.itemView.tag as Int)
                    }
//                    adapter.remove(swipedPosition)
//                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView

                    // not sure why, but this method gets called for viewholder that are already swiped away
                    if (viewHolder.bindingAdapterPosition == -1) {
                        // not interested in those
                        return
                    }
                    if (!initiated) {
                        init()
                    }

                    // draw red background
                    background!!.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    background!!.draw(c)

                    // draw x mark
                    val itemHeight = itemView.bottom - itemView.top
                    val intrinsicWidth = xMark!!.intrinsicWidth
                    val intrinsicHeight = xMark!!.intrinsicWidth
                    val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
                    val xMarkRight = itemView.right - xMarkMargin
                    val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                    val xMarkBottom = xMarkTop + intrinsicHeight
                    xMark!!.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)
                    xMark!!.draw(c)
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(binding.personList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
