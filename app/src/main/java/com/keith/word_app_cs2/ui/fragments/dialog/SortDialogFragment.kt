package com.keith.word_app_cs2.ui.fragments.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.keith.word_app_cs2.R
import com.keith.word_app_cs2.databinding.FragmentSortDialogBinding
import kotlinx.coroutines.launch

class SortDialogFragment : DialogFragment() {
    private val viewModel: SortDialogViewModel by viewModels()
    private lateinit var binding: FragmentSortDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSortDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnClickListener { dismiss() }
        lifecycleScope.launch {
            viewModel.error.collect {
                val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red)
                )
                snackbar.show()
            }
        }
        lifecycleScope.launch {
            viewModel.finish.collect { (sortOrder, sortBy) ->
                val result = Bundle().apply {
                    putString("sort_order", sortOrder)
                    putString("sort_by", sortBy)
                }
                setFragmentResult("sort_options", result)
                dismiss()
            }
        }
        binding.mbDone.setOnClickListener {
            val sortOrderId = binding.rgSortOrder.checkedRadioButtonId
            val sortById = binding.rgSortBy.checkedRadioButtonId
            val ascId = binding.rbAscending.id
            val descId = binding.rbDescending.id
            val titleId = binding.rbTitle.id
            val dateId = binding.rbDate.id
            viewModel.onDone(
                sortOrderId,
                sortById,
                ascId,
                descId,
                titleId,
                dateId)
        }
    }
}