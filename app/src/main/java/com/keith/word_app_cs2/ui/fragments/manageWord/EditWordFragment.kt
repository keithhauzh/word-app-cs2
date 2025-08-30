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
import androidx.navigation.fragment.navArgs
import com.keith.word_app_cs2.R
import com.keith.word_app_cs2.databinding.FragmentBaseManageWordBinding
import kotlinx.coroutines.launch

class EditWordFragment: Fragment() {
    private val viewModel: EditWordViewModel by viewModels()
    private lateinit var binding: FragmentBaseManageWordBinding
    private val args: EditWordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseManageWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWord(args.wordId)
        displayWord()
        submitUpdatedWord()
        lifecycleScope.launch {
            viewModel.finish.collect {
                setFragmentResult("manage_word", Bundle())
                findNavController().popBackStack()
            }
        }
    }
    fun displayWord() {
        lifecycleScope.launch {
            viewModel.word.collect {
                binding.run {
                    materialToolbar.title = "Update Word"
                    etTitle.setText(it.title)
                    etMeaning.setText(it.meaning)
                    etSynonyms.setText(it.synonyms)
                    etDetails.setText(it.details)
                    mbSubmit.setText(R.string.update)
                }
            }
        }
    }
    fun submitUpdatedWord() {
        binding.run {
            mbSubmit.setOnClickListener {
                val title = etTitle.text.toString()
                val meaning = etMeaning.text.toString()
                val synonyms = etSynonyms.text.toString()
                val details = etDetails.text.toString()
                viewModel.updateWord(title, meaning, synonyms, details)
            }
        }
    }
}