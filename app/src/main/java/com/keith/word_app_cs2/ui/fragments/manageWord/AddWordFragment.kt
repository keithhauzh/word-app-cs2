package com.keith.word_app_cs2.ui.fragments.manageWord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack()
            }
        }
        binding.mbSubmit.setOnClickListener {
            viewModel.addWord(
                title = binding.etTitle.text.toString(),
                meaning = binding.etMeaning.text.toString(),
                synonyms = binding.etSynonyms.text.toString(),
                details = binding.etDetails.text.toString()
            )
        }
    }
}