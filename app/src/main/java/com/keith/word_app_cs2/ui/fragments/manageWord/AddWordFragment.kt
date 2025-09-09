package com.keith.word_app_cs2.ui.fragments.manageWord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.keith.word_app_cs2.R
import com.keith.word_app_cs2.databinding.FragmentBaseManageWordBinding
import kotlinx.coroutines.launch

class AddWordFragment : Fragment() {
    private val viewModel: AddWordViewModel by viewModels()
    private lateinit var binding: FragmentBaseManageWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseManageWordBinding.inflate(
            layoutInflater,
            container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        error()
        finishObserver()

        binding.mbSubmit.setOnClickListener {
            viewModel.addWord(
                title = binding.etTitle.text.toString(),
                meaning = binding.etMeaning.text.toString(),
                synonyms = binding.etSynonyms.text.toString(),
                details = binding.etDetails.text.toString()
            )
        }
    }
    private fun error() {
        lifecycleScope.launch {
            viewModel.error.collect {
                val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red)
                )
                snackbar.show()
            }
        }
    }

    private fun finishObserver() {
        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack()
            }
        }
    }
}