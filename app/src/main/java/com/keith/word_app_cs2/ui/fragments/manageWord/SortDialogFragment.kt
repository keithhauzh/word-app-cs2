package com.keith.word_app_cs2.ui.fragments.manageWord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.keith.word_app_cs2.databinding.FragmentSortDialogBinding

class SortDialogFragment : DialogFragment() {
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
        binding.mbDone.setOnClickListener {
            val sortOrder = when(binding.rgSortOrder.checkedRadioButtonId) {
                binding.rbAscending.id -> "ascending"
                binding.rbDescending.id -> "descending"
                else -> "ascending"
            }
            val sortBy = when(binding.rgSortBy.checkedRadioButtonId) {
                binding.rbTitle.id -> "title"
                binding.rbDate.id -> "date"
                else -> "title"
            }
            val result = Bundle().apply {
                putString("sortOrder", sortOrder)
                putString("sortBy", sortBy)
            }
            setFragmentResult("sort_options", result)
            dismiss()
        }
        //add error handling
    }
}