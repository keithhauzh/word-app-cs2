package com.keith.word_app_cs2.ui.fragments.completedWord

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.keith.word_app_cs2.databinding.FragmentCompletedWordBinding
import com.keith.word_app_cs2.ui.adapter.WordsAdapter
import kotlinx.coroutines.launch
import com.keith.word_app_cs2.R

class CompletedWordFragment : Fragment() {
    private lateinit var binding: FragmentCompletedWordBinding
    private val viewModel: CompletedWordViewModel by viewModels()
    private lateinit var adapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedWordBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        adapter = WordsAdapter(emptyList()){
            val action = CompletedWordFragmentDirections.actionCompletedWordFragmentToWordDetailsFragment(
                wordId = it.id!!,
            )
            findNavController().navigate(action)
        }

        binding.navView.setupWithNavController(navController)
        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWords.adapter = adapter

        setFragmentResultListener("manage_word") { _, _ ->
            viewModel.getWords()
        }

        lifecycleScope.launch {
            viewModel.words.collect { words ->
                adapter.setWords(words)
                binding.llEmpty.visibility = if(words.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        viewModel.getWords()
    }
}