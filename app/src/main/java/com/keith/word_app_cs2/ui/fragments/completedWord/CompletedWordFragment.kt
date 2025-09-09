package com.keith.word_app_cs2.ui.fragments.completedWord

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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

        setupNavigation()
        setupAdapter()
        setupFragmentResultListeners()
        sortAndSearch()

        lifecycleScope.launch {
            viewModel.words.collect { words ->
                adapter.setWords(words)
                binding.llEmpty.visibility = if(words.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        viewModel.getWords()
    }

    private fun setupNavigation() {
        val navHostFragment = requireActivity().supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
    }

    private fun setupAdapter() {
        adapter = WordsAdapter(emptyList()){
            val action = CompletedWordFragmentDirections.actionCompletedWordFragmentToWordDetailsFragment(
                wordId = it.id!!,
            )
            findNavController().navigate(action)
        }
        binding.rvWords.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWords.adapter = adapter
    }

    private fun setupFragmentResultListeners() {
        setFragmentResultListener("manage_word") { _, _ ->
            viewModel.getWords()
        }
        setFragmentResultListener("sort_options") {_, bundle ->
            val sortOder = bundle.getString("sort_order", "ascending")
            val sortBy = bundle.getString("sort_by", "title")
            viewModel.sortWords(sortOder, sortBy)
        }
    }

    private fun sortAndSearch() {
        binding.ivSort.setOnClickListener {
            val action = CompletedWordFragmentDirections.actionCompletedWordFragmentToSortDialogFragment()
            findNavController().navigate(action)
        }
        binding.etSearch.addTextChangedListener {
            viewModel.search(it.toString().trim())
        }
    }
}